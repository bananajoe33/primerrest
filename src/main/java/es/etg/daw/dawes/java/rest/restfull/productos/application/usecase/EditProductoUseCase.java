package es.etg.daw.dawes.java.rest.restfull.productos.application.usecase;


import es.etg.daw.dawes.java.rest.restfull.productos.application.command.EditProductoCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.error.ProductoNotFoundException;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Producto;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.repository.ProductoRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class EditProductoUseCase {

    private final ProductoRepository productoRepository;
    
    public Producto update(EditProductoCommand command){
        return productoRepository.getById(command.id())
                .map(p -> {  //Actualizamos los atributos del objeto
                    p.setNombre(command.nombre());
                    p.setPrecio(command.precio());
                    return productoRepository.save(p);})
                .orElseThrow(() -> new ProductoNotFoundException(command.id()));

    }

}