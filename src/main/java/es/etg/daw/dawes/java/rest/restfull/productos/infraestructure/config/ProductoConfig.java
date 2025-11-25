package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.CreateProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.DeleteProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.EditProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.producto.FindProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.producto.CreateProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.producto.DeleteProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.producto.EditProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.producto.FindProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.ProductoRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository.ProductoEntityJpaRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository.ProductoJpaRepositoryImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProductoConfig {

    // Repositorio JPA CRUD generado por Spring
    private final ProductoEntityJpaRepository productoEntityRepository;

    // Adaptador que implementa ProductoRepository
    @Bean
    public ProductoRepository productoRepository() {
        return new ProductoJpaRepositoryImpl(productoEntityRepository);
    }

    // --- Use Cases ---

    @Bean
    public CreateProductoUseCase createProductoUseCase(ProductoRepository productoRepository) {
        return new CreateProductoUseCase(productoRepository);
    }

    @Bean
    public FindProductoUseCase findProductoUseCase(ProductoRepository productoRepository) {
        return new FindProductoUseCase(productoRepository);
    }

    @Bean
    public DeleteProductoUseCase deleteProductoUseCase(ProductoRepository productoRepository) {
        return new DeleteProductoUseCase(productoRepository);
    }

    @Bean
    public EditProductoUseCase editProductoUseCase(ProductoRepository productoRepository) {
        return new EditProductoUseCase(productoRepository);
    }

    // --- Services ---

    @Bean
    public CreateProductoService createProductoService(CreateProductoUseCase createProductoUseCase) {
        return new CreateProductoService(createProductoUseCase);
    }

    @Bean
    public FindProductoService findProductoService(FindProductoUseCase findProductoUseCase) {
        return new FindProductoService(findProductoUseCase);
    }

    @Bean
    public DeleteProductoService deleteProductoService(DeleteProductoUseCase deleteProductoUseCase) {
        return new DeleteProductoService(deleteProductoUseCase);
    }

    @Bean
    public EditProductoService editProductoService(EditProductoUseCase editProductoUseCase) {
        return new EditProductoService(editProductoUseCase);
    }
}
