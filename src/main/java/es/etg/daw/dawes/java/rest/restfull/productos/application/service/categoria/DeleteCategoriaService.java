package es.etg.daw.dawes.java.rest.restfull.productos.application.service.categoria;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import es.etg.daw.dawes.java.rest.restfull.productos.application.usecase.categoria.DeleteCategoriaUseCase;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeleteCategoriaService {

    private final DeleteCategoriaUseCase deleteCategoriaUseCase;

    @CacheEvict
    @CachePut
    public void delete(CategoriaId id) {
        deleteCategoriaUseCase.delete(id);
    }

    /**
     *
     * private final DeleteProductoUseCase deleteProductoUseCase;

     *      *@CacheEvict
     * @CachePut public void delete(ProductoId id){
     * deleteProductoUseCase.delete(id); }
     *
     *
     */
}
