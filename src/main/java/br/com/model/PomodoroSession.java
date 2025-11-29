package br.com.model;

import java.util.Date;

public class PomodoroSession {
    private Long id;
    private Long tarefaId;
    private Long duration;
    private Date createdAt;

    public PomodoroSession() {}

    public PomodoroSession(Long id, Long tarefaId, Long duration, Date createdAt) {
        this.id = id;
        this.tarefaId = tarefaId;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
