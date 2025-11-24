package br.com.dto.pomodoroSession;

import br.com.utils.Status;
import br.com.exceptions.ValidationException;

import java.util.Date;
import java.util.HashMap;

public class PomodoroSessionRequest {
    private Long tarefaId;
    private Long duration;
    private Date createdAt;
    private Date updatedAt;
    private Status status;

    public PomodoroSessionRequest() {}

    public PomodoroSessionRequest(Long tarefaId, Long duration, Date createdAt, Date updatedAt, Status status) {
        this.tarefaId = tarefaId;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
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

    public void validate() {
        HashMap<String, String> errors = new HashMap<>();

        if (tarefaId == null) {
            errors.put("name", "The name field is required.");
        }

        if (duration == null) {
            errors.put("duration", "The duration field is required.");
        }

        if (createdAt == null) {
            errors.put("createdAt", "The createdAt field is required.");
        }

        if (updatedAt == null) {
            errors.put("updatedAt", "The updatedAt field is required.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}

