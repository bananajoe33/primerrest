package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.repository;

import java.util.List;
import java.util.Optional;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.CategoriaRepository;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.entity.CategoriaEntity;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.mapper.CategoriaMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaJpaRepositoryImpl implements CategoriaRepository {

    private final CategoriaEntityJpaRepository repository;

    @Override
    public Categoria save(Categoria c) {
        CategoriaEntity entity = CategoriaMapper.toEntity(c);
        return CategoriaMapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Categoria> getAll() {
        return CategoriaMapper.toDomain(repository.findAll());
    }

    @Override
    public Optional<Categoria> getById(CategoriaId id) {
        Optional<CategoriaEntity> entityOpt = repository.findById(id.getValue());
        return entityOpt.map(CategoriaMapper::toDomain);
    }

    @Override
    public void deteteById(CategoriaId id) { 
        repository.deleteById(id.getValue());
    }

    @Override
    public Optional<Categoria> getByName(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getByName'");
    }
}
