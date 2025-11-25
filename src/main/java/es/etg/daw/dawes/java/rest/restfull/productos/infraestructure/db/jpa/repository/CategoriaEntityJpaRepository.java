package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.entity.CategoriaEntity;

@Repository
public interface CategoriaEntityJpaRepository extends JpaRepository<CategoriaEntity, Integer> {

    // Obtiene una categoría por su nombre
    public CategoriaEntity findByNombre(String nombre);
}
