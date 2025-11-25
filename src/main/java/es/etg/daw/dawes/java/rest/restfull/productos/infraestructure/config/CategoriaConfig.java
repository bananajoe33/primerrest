package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.CreateCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.DeleteCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.EditCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria.FindCategoriaService;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.categoria.CreateCategoriaUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.categoria.DeleteCategoriaUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.categoria.EditCategoriaUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.categoria.FindCategoriaUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.CategoriaRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository.CategoriaEntityJpaRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository.CategoriaJpaRepositoryImpl;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CategoriaConfig {

    // Repositorio JPA CRUD generado por Spring
    private final CategoriaEntityJpaRepository categoriaEntityRepository;

    // Adaptador que implementa CategoriaRepository
    @Bean
    public CategoriaRepository categoriaRepository() {
        return new CategoriaJpaRepositoryImpl(categoriaEntityRepository);
    }

    // --- Use Cases ---

    @Bean
    public CreateCategoriaUseCase createCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new CreateCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public FindCategoriaUseCase findCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new FindCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public DeleteCategoriaUseCase deleteCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new DeleteCategoriaUseCase(categoriaRepository);
    }

    @Bean
    public EditCategoriaUseCase editCategoriaUseCase(CategoriaRepository categoriaRepository) {
        return new EditCategoriaUseCase(categoriaRepository);
    }

    // --- Services ---

    @Bean
    public CreateCategoriaService createCategoriaService(CreateCategoriaUseCase createCategoriaUseCase) {
        return new CreateCategoriaService(createCategoriaUseCase);
    }

    @Bean
    public FindCategoriaService findCategoriaService(FindCategoriaUseCase findCategoriaUseCase) {
        return new FindCategoriaService(findCategoriaUseCase);
    }

    @Bean
    public DeleteCategoriaService deleteCategoriaService(DeleteCategoriaUseCase deleteCategoriaUseCase) {
        return new DeleteCategoriaService(deleteCategoriaUseCase);
    }

    @Bean
    public EditCategoriaService editCategoriaService(EditCategoriaUseCase editCategoriaUseCase) {
        return new EditCategoriaService(editCategoriaUseCase);
    }
}

