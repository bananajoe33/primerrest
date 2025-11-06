package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto;

import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.validation.NombradoCategoria;
import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
                @NotBlank(message = "{categoria.valid.nombre.no_vacio}")
                @NombradoCategoria(message = "{categoria.valid.nombre.nombrado_validation}")
                    String nombre) {
}

