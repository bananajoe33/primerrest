package es.etg.daw.dawes.java.rest.restfull;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Producto;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.ProductoId;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.ProductoRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.ProductoFactory;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.ProductoRepositoryMockImpl;

public class ProductoRepositoryMockImplTest {

    ProductoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductoRepositoryMockImpl();
    }

    @Test
    void save() {
        var producto = ProductoFactory.create();

        Producto saved = repository.save(producto);

        assertAll(
            () -> assertNotNull(saved, "El producto guardado no debe ser nulo"),
            () -> assertNotNull(saved.getId(), "El producto guardado debe tener un ID asignado"),
            () -> assertTrue(repository.getById(saved.getId()).isPresent(), "El producto guardado debe poder recuperarse"),
            () -> assertEquals(saved, repository.getById(saved.getId()).get(), "El producto recuperado debe ser el mismo")
        );
    }

    @Test
    void getAll() {
        var productos = repository.getAll();

        assertAll(
            () -> assertNotNull(productos, "La lista de productos no debe ser nula"),
            () -> assertEquals(ProductoFactory.getDemoData().size(), productos.size(), "El número de productos debe coincidir con los datos demo")
        );
    }

    @Test
    void getById() {
        var productos = repository.getAll();
        var idExistente = productos.get(0).getId();

        Optional<Producto> encontrado = repository.getById(idExistente);

        assertAll(
            () -> assertTrue(encontrado.isPresent(), "El producto con ese ID debe existir"),
            () -> assertEquals(idExistente, encontrado.get().getId(), "El ID del producto recuperado debe coincidir")
        );
    }

    @Test
    void getById_Inexistente() {
        ProductoId idInexistente = new ProductoId(9999);

        Optional<Producto> encontrado = repository.getById(idInexistente);

        assertTrue(encontrado.isEmpty(), "El producto no debe existir en el repositorio");
    }

    @Test
    void deteteById() {
        var producto = ProductoFactory.create();
        Producto saved = repository.save(producto);
        ProductoId id = saved.getId();

        repository.deteteById(id);

        assertTrue(repository.getById(id).isEmpty(), "El producto debe eliminarse correctamente del repositorio");
    }
}
