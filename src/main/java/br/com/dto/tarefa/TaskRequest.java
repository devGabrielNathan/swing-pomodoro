package br.com.dto.tarefa;

import br.com.exceptions.ValidationException;
import br.com.model.Category;

import java.util.HashMap;
import java.util.Objects;

public class TaskRequest {
    private String title;
    private String description;
    private Long category;
    private Long plannedTotalPomodoros;
    private Long CompletedTotalPomodoros;

    public TaskRequest() {}

    public TaskRequest(String title, String description, Long category, Long plannedTotalPomodoros, Long completedTotalPomodoros) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.plannedTotalPomodoros = plannedTotalPomodoros;
        CompletedTotalPomodoros = completedTotalPomodoros;
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getPlannedTotalPomodoros() {
        return plannedTotalPomodoros;
    }

    public void setPlannedTotalPomodoros(Long plannedTotalPomodoros) {
        this.plannedTotalPomodoros = plannedTotalPomodoros;
    }

    public Long getCompletedTotalPomodoros() {
        return CompletedTotalPomodoros;
    }

    public void setCompletedTotalPomodoros(Long completedTotalPomodoros) {
        CompletedTotalPomodoros = completedTotalPomodoros;
    }

    public void validate() {
        HashMap<String, String> errors = new HashMap<>();

        if (Objects.isNull(title) || title.isBlank()) {
            errors.put("title", "The title field is required.");
        }

//        if (Objects.isNull(description) || description.isBlank()) {
//            errors.put("description", "The description field is required.");
//        }

//        if (Objects.isNull(category)) {
//            errors.put("category", "The category field is required.");
//        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
