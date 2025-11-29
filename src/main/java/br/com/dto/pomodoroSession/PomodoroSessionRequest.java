package br.com.dto.pomodoroSession;

import java.util.Date;

public class PomodoroSessionRequest {
    private Long tarefaId;
    private Long duration;
    private Date createdAt;

    public PomodoroSessionRequest() {}

    public PomodoroSessionRequest(Long tarefaId, Long duration, Date createdAt) {
        this.tarefaId = tarefaId;
        this.duration = duration;
        this.createdAt = createdAt;
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
