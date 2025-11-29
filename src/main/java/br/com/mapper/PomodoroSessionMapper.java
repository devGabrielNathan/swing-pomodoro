package br.com.mapper;

import br.com.dto.pomodoroSession.PomodoroSessionRequest;
import br.com.dto.pomodoroSession.PomodoroSessionResponse;
import br.com.model.PomodoroSession;

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
        return entity;
    }
}
