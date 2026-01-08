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

import es.etg.daw.dawes.java.rest.restfull.productos.application.command.producto.CreateProductoCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.application.command.producto.EditProductoCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.CreateProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.DeleteProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.EditProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.FindProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Producto;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.ProductoId;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.mapper.ProductoMapper;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.ProductoRequest;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.ProductoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    @Value("${api.version}")
    private String apiVersion;

    private final CreateProductoService createProductoService;
    private final FindProductoService findProductoService;
    private final DeleteProductoService deleteProductoService;
    private final EditProductoService editProductoService;

    // Método privado para validar la versión de la API
    private void validarVersion() {
        if (!"1.0".equals(apiVersion)) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Versión del API incorrecta"
            );
        }
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> createProducto(
            @Valid @RequestBody ProductoRequest productoRequest) {
        validarVersion(); // Validación de versión
        CreateProductoCommand comando = ProductoMapper.toCommand(productoRequest);
        Producto producto = createProductoService.createProducto(comando);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductoMapper.toResponse(producto));
    }

    @GetMapping
    public List<ProductoResponse> allProductos() {
        validarVersion(); // Validación de versión
        return findProductoService.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable int id) {
        validarVersion(); // Validación de versión
        deleteProductoService.delete(new ProductoId(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ProductoResponse editProducto(@PathVariable int id, @RequestBody ProductoRequest productoRequest) {
        validarVersion(); // Validación de versión
        EditProductoCommand comando = ProductoMapper.toCommand(id, productoRequest);
        Producto producto = editProductoService.update(comando);
        return ProductoMapper.toResponse(producto);
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
