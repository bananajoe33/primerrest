package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.CategoriaRepository;

@Repository
public class CategoriaRepositoryMockImpl implements CategoriaRepository {

    private final Map<CategoriaId, Categoria> categorias = CategoriaFactory.getDemoData();

    // Mejora para calcular el id de la creación que viene vacío
    @Override
    public Categoria save(Categoria c) {
        // create
        if (c.getId() == null) c.setId(new CategoriaId(obtenerSiguienteId()));

        categorias.put(c.getId(), c);
        return c;
    }

    private int obtenerSiguienteId() {
        CategoriaId ultimo = null;
        if (!categorias.isEmpty()) {
            Collection<Categoria> lista = categorias.values();

            for (Categoria cat : lista) {
                ultimo = cat.getId();
            }
        }
        return ultimo.getValue() + 1;
    }

    @Override
    public Optional<Categoria> getById(CategoriaId id) {
        // Un optional puede tener un valor o no. Si no existe la categoría devuelve Optional.empty
        return Optional.ofNullable(categorias.get(id));
    }

    @Override
    public void deteteById(CategoriaId id) {
        categorias.remove(id);
    }

    @Override
    public List<Categoria> getAll() {
        return new ArrayList<>(categorias.values());
    }

    @Override
    public Optional<Categoria> getByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByName'");
    }

    
}

