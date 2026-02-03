package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Tag(
    name = "Productos",
    description = "Operaciones relacionadas con la gestión de productos"
)
public class ProductoController {

    private final CreateProductoService createProductoService;
    private final FindProductoService findProductoService;
    private final DeleteProductoService deleteProductoService;
    private final EditProductoService editProductoService;

    @PostMapping
    @Operation(
        summary = "Crea un producto",
        description = "Crea un nuevo producto en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProductoResponse> createProducto(
            @Valid @RequestBody ProductoRequest productoRequest) {

        CreateProductoCommand comando = ProductoMapper.toCommand(productoRequest);
        Producto producto = createProductoService.createProducto(comando);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductoMapper.toResponse(producto));
    }

    @GetMapping
    @Operation(
        summary = "Obtiene el listado de productos",
        description = "Busca en la base de datos todos los productos y sus detalles"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de productos generado"),
        @ApiResponse(responseCode = "404", description = "No hay productos en la base de datos")
    })
    public List<ProductoResponse> allProductos() {
        return findProductoService.findAll()
                .stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Elimina un producto",
        description = "Elimina un producto a partir de su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<?> deleteProducto(@PathVariable int id) {
        deleteProductoService.delete(new ProductoId(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualiza un producto",
        description = "Actualiza los datos de un producto existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ProductoResponse editProducto(
            @PathVariable int id,
            @RequestBody ProductoRequest productoRequest) {

        EditProductoCommand comando = ProductoMapper.toCommand(id, productoRequest);
        Producto producto = editProductoService.update(comando);
        return ProductoMapper.toResponse(producto);
    }

    // Manejo de errores de validación
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
