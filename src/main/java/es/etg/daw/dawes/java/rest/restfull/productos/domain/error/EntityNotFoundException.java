package es.etg.daw.dawes.java.rest.restfull.productos.domain.error;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entity) {
        super("%s no encontrado".formatted(entity));
    }

    public EntityNotFoundException(String entity, int id){
        super("%s con id %s no se encuentra".formatted(entity, id));
    }
    
}