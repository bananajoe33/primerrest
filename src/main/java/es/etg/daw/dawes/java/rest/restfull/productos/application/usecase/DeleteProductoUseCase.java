package es.etg.daw.dawes.java.rest.restfull.productos.application.usecase;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteProductoUseCase {

     private final ProductoRepository productoRepository;

    public void delete(int id){

    }
    
}
