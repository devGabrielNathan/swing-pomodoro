package br.com.dto.task;

import br.com.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Objects;

public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;

    public TaskRequest() {}

    public TaskRequest(Long id, String title, String description, Long categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }

    public TaskRequest(String title, String description, Long categoryId) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void validate() {
        HashMap<String, String> errors = new HashMap<>();

        if (Objects.isNull(title) || title.isBlank()) {
            errors.put("title", "The title field is required.");
        }

//        if (Objects.isNull(description) || description.isBlank()) {
//            errors.put("description", "The description field is required.");
//        }

//        if (Objects.isNull(categoryId)) {
//            errors.put("categoryId", "The categoryId field is required.");
//        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
