package es.etg.daw.dawes.java.rest.restfull.productos.application.command.categoria;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

//Esta clase tiene los datos necesarios para editar un Producto
@Getter
@AllArgsConstructor
@Accessors(fluent = true) // Así los getters no llevan prefijo get
public class EditCategoriaCommand {
    
    private final CategoriaId id; //Lo cambiamos
    private final String nombre;
    
}
