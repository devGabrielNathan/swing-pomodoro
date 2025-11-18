package br.com.dto.tarefa;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Long category;
    private Long plannedTotalPomodoros;
    private Long CompletedTotalPomodoros;

    public TaskResponse() {}

    public TaskResponse(Long id, String title, String description, Long category, Long plannedTotalPomodoros, Long completedTotalPomodoros) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.plannedTotalPomodoros = plannedTotalPomodoros;
        CompletedTotalPomodoros = completedTotalPomodoros;
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
}
