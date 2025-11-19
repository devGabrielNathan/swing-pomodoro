package br.com.mapper;

import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.sessaoPomodoro.PomodoroSessionResponse;
import br.com.model.PomodoroSession;
import br.com.utils.Status;

import java.util.Date;

public class PomodoroSessionMapper {

    public PomodoroSessionResponse toResponse(PomodoroSession entity) {
        if (entity == null) {
            return null;
        }
        PomodoroSessionResponse response = new PomodoroSessionResponse();
        response.setId(entity.getId());
        response.setTarefaId(entity.getTarefaId());
        response.setDuration(entity.getDuration());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setStatus(entity.getStatus());
        return response;
    }

    public PomodoroSession toEntity(PomodoroSessionRequest request) {
        if (request == null) {
            return null;
        }
        PomodoroSession entity = new PomodoroSession();
        entity.setTarefaId(request.getTarefaId());
        entity.setDuration(request.getDuration());
        entity.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : new Date());
        entity.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : new Date());
        entity.setStatus(request.getStatus() != null ? request.getStatus() : Status.IN_PROGRESS);
        return entity;
    }
}
