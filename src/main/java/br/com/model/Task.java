package br.com.model;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long category;
    private Long plannedTotalPomodoros;
    private Long completedTotalPomodoros;

    public Task() {}

    public Task(Long id, String title, String description, Long category, Long plannedTotalPomodoros, Long completedTotalPomodoros) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.plannedTotalPomodoros = plannedTotalPomodoros;
        this.completedTotalPomodoros = completedTotalPomodoros;
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
        return completedTotalPomodoros;
    }

    public void setCompletedTotalPomodoros(Long completedTotalPomodoros) {
        this.completedTotalPomodoros = completedTotalPomodoros;
    }
}
