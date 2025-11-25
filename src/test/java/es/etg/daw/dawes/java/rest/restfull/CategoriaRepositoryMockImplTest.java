package es.etg.daw.dawes.java.rest.restfull;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.CategoriaRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.CategoriaFactory;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.CategoriaRepositoryMockImpl;

public class CategoriaRepositoryMockImplTest {

    CategoriaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CategoriaRepositoryMockImpl();
    }

    @Test
    void save() {
        var categoria = CategoriaFactory.create();

        Categoria saved = repository.save(categoria);

        assertAll(
            () -> assertNotNull(saved, "La categoría guardada no debe ser nula"),
            () -> assertNotNull(saved.getId(), "La categoría guardada debe tener un ID asignado"),
            () -> assertTrue(repository.getById(saved.getId()).isPresent(), "La categoría guardada debe poder recuperarse"),
            () -> assertEquals(saved, repository.getById(saved.getId()).get(), "La categoría recuperada debe ser la misma")
        );
    }

    @Test
    void getAll() {
        var categorias = repository.getAll();

        assertAll(
            () -> assertNotNull(categorias, "La lista de categorías no debe ser nula"),
            () -> assertEquals(CategoriaFactory.getDemoData().size(), categorias.size(), "El número de categorías debe coincidir con los datos demo")
        );
    }

    @Test
    void getById() {
        var categorias = repository.getAll();
        var idExistente = categorias.get(0).getId();

        Optional<Categoria> encontrada = repository.getById(idExistente);

        assertAll(
            () -> assertTrue(encontrada.isPresent(), "La categoría con ese ID debe existir"),
            () -> assertEquals(idExistente, encontrada.get().getId(), "El ID de la categoría recuperada debe coincidir")
        );
    }

    @Test
    void getById_Inexistente() {
        CategoriaId idInexistente = new CategoriaId(9999);

        Optional<Categoria> encontrada = repository.getById(idInexistente);

        assertTrue(encontrada.isEmpty(), "La categoría no debe existir en el repositorio");
    }

    @Test
    void deteteById() {
        var categoria = CategoriaFactory.create();
        Categoria saved = repository.save(categoria);
        CategoriaId id = saved.getId();

        repository.deteteById(id);

        assertTrue(repository.getById(id).isEmpty(), "La categoría debe eliminarse correctamente del repositorio");
    }
}
