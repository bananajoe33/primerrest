package es.etg.daw.dawes.java.rest.restfull.productos.domain.repository;

import java.util.Optional;

import es.etg.daw.dawes.java.rest.restfull.common.domain.repository.CRUDRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;

public interface CategoriaRepository extends CRUDRepository<Categoria, CategoriaId> {

    public Optional<Categoria> getByName(String name);
    
}
