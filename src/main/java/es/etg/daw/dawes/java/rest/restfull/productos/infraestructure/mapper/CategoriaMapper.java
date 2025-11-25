package es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.mapper;

import java.util.ArrayList;
import java.util.List;

import es.etg.daw.dawes.java.rest.restfull.productos.application.command.categoria.CreateCategoriaCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.application.command.categoria.EditCategoriaCommand;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Categoria;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.jpa.entity.CategoriaEntity;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaRequest;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaResponse;

public class CategoriaMapper {

    // Convertir de request a comando de creación
    public static CreateCategoriaCommand toCommand(CategoriaRequest categoriaRequest) {
        return new CreateCategoriaCommand(categoriaRequest.nombre());
    }

    // Convertir de request a comando de edición
    public static EditCategoriaCommand toCommand(int id, CategoriaRequest categoriaRequest) {
        return new EditCategoriaCommand(new CategoriaId(id), categoriaRequest.nombre());
    }

    // Convertir de dominio a response
    public static CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId().getValue(),
                categoria.getNombre(),
                null // si quieres agregar algún otro dato en el futuro
        );
    }

    // Convertir de dominio a entidad JPA
    public static CategoriaEntity toEntity(Categoria categoria) {
        CategoriaId id = categoria.getId();
        CategoriaEntity.CategoriaEntityBuilder builder = CategoriaEntity.builder()
                .nombre(categoria.getNombre());

        // Solo asignar ID si ya existe (actualización)
        if (id != null && id.getValue() != null) {
            builder.id(id.getValue());
        }

        return builder.build();
    }

    // Convertir de entidad JPA a dominio
    public static Categoria toDomain(CategoriaEntity entity) {
        return Categoria.builder()
                .id(new CategoriaId(entity.getId()))
                .nombre(entity.getNombre())
                .createdAt(null) // si quieres mapear createdAt desde la entidad, cambia aquí
                .build();
    }

    // Convertir lista de entidades a lista de dominio
    public static List<Categoria> toDomain(List<CategoriaEntity> lista) {
        List<Categoria> categorias = new ArrayList<>();
        for (CategoriaEntity ce : lista) {
            categorias.add(toDomain(ce));
        }
        return categorias;
    }
}
