package br.com.dto.category;

import br.com.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Objects;

public class CategoryRequest {
    private Long id;
    private String name;
    private String description;

    public CategoryRequest() {}

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // TODO: Transformar para annotation
    public void validate() {
        HashMap<String, String> errors = new HashMap<>();

        if (Objects.isNull(name) || name.isBlank()) {
            errors.put("name", "The name field is required.");
        }

        if (Objects.isNull(description) || description.isBlank()) {
            errors.put("description", "The description field is required.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}

