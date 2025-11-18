package br.com.dto.sessaoPomodoro;

import br.com.utils.Status;

import java.util.Date;

public class PomodoroSessionResponse {
    private Long id;
    private Long tarefaId;
    private Long duration;
    private Date createdAt;
    private Date updatedAt;
    private Status status;

    public PomodoroSessionResponse() {}

    public PomodoroSessionResponse(Long id, Long tarefaId, Long duration, Date createdAt, Date updatedAt, Status status) {
        this.id = id;
        this.tarefaId = tarefaId;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

