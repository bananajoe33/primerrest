package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.etg.daw.dawes.java.rest.restfull.productos.application.service.CreateProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.FindProductoService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.CreateProductoUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.FindProductoUseCase;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProductoConfig {
    
    @Bean
    public CreateProductoUseCase createProductoUseCase() {
        return new CreateProductoUseCase();
    }
    @Bean
    public CreateProductoService createProductoService(){
        return new CreateProductoService(createProductoUseCase());
    }

    @Bean
    public FindProductoUseCase findProductoUseCase(){
        return new FindProductoUseCase();
    }

    @Bean
    public FindProductoService findProductoService(){
        return new FindProductoService(findProductoUseCase());
    }
}