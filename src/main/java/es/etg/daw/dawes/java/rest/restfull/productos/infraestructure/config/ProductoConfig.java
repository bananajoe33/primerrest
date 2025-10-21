package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.etg.daw.dawes.java.rest.restfull.productos.application.service.CreateProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.DeleteProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.EditProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.FindProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.CreateProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.DeleteProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.EditProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.FindProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.ProductoRepositoryMockImpl;

@Configuration
public class ProductoConfig {

     @Bean
    public CreateProductoUseCase createProductoUseCase() {
		//Añadimos en la llamada una instancia de nuestro MOCK.
        return new CreateProductoUseCase(new ProductoRepositoryMockImpl());
    }
    @Bean
    public CreateProductoService createProductoService() {
        return new CreateProductoService(createProductoUseCase());
    }

    @Bean
    public FindProductoUseCase findProductoUseCase() {
        return new FindProductoUseCase();
    }

    @Bean
    public FindProductoService findProductoService() {
        return new FindProductoService(findProductoUseCase());
    }

    // 👇 NUEVOS BEANS PARA DELETE
    @Bean
    public DeleteProductoUseCase deleteProductoUseCase() {
        return new DeleteProductoUseCase();
    }

    @Bean
    public DeleteProductoService deleteProductoService() {
        return new DeleteProductoService(deleteProductoUseCase());
    }

    @Bean  
    public EditProductoService editProductoService(){
        return new EditProductoService(editProductoUseCase());
    }

    @Bean

    public EditProductoUseCase editProductoUseCase(){
        return new EditProductoUseCase();
    }
}
