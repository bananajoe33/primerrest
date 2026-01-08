package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.etg.daw.dawes.java.rest.restfull.productos.application.command.categoria.CreateCategoriaCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.application.command.categoria.EditCategoriaCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.CreateCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.DeleteCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.EditCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.FindCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.mapper.CategoriaMapper;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaRequest;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias") // La URL será /categorias
@RequiredArgsConstructor
public class CategoriaController {

    @Value("${api.version}")
    private String apiVersion;

    private final CreateCategoriaService createCategoriaService;
    private final FindCategoriaService findCategoriaService;
    private final DeleteCategoriaService deleteCategoriaService;
    private final EditCategoriaService editCategoriaService;

    
    private void validarVersion() {
        if (!"1.0".equals(apiVersion)) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Versión del API incorrecta"
            );
        }
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> createCategoria(
            @Valid @RequestBody CategoriaRequest categoriaRequest) {
        validarVersion(); // Validación de versión
        CreateCategoriaCommand comando = CategoriaMapper.toCommand(categoriaRequest);
        Categoria categoria = createCategoriaService.createCategoria(comando);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriaMapper.toResponse(categoria));
    }

    @GetMapping
    public List<CategoriaResponse> allCategorias() {
        validarVersion(); // Validación de versión
        return findCategoriaService.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable int id) {
        validarVersion(); // Validación de versión
        deleteCategoriaService.delete(new CategoriaId(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public CategoriaResponse editCategoria(@PathVariable int id, @RequestBody CategoriaRequest categoriaRequest) {
        validarVersion(); // Validación de versión
        EditCategoriaCommand comando = CategoriaMapper.toCommand(id, categoriaRequest);
        Categoria categoria = editCategoriaService.update(comando);
        return CategoriaMapper.toResponse(categoria);
    }

    // Manejo de errores de validación
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
