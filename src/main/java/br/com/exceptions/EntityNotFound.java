package br.com.exceptions;

public class EntityNotFound extends RuntimeException {
    private final Long id;
    private final String name;

    public EntityNotFound(Long id, String name) {
        super();
        this.name = name;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String message() {
        return "Não foi possível encontrar a entidade " + getName() + " com o id " + getId();
    }
}
