package br.com.service;

import br.com.dao.PomodoroSessionDAO;
import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.sessaoPomodoro.PomodoroSessionResponse;
import br.com.exceptions.EntityNotFound;
import br.com.mapper.PomodoroSessionMapper;
import br.com.model.PomodoroSession;

import java.util.List;
import java.util.Objects;

public record PomodoroSessionService(PomodoroSessionDAO dao, PomodoroSessionMapper mapper) {

    public PomodoroSessionResponse findById(Long id) {
        PomodoroSession session = dao.findById(id);
        return mapper.toResponse(session);
    }

    public List<PomodoroSessionResponse> findAll() {
        List<PomodoroSession> sessions = dao.findAll();
        return sessions.stream().map(mapper::toResponse).toList();
    }

    public PomodoroSessionResponse create(PomodoroSessionRequest request) {
        PomodoroSession entity = mapper.toEntity(request);
        PomodoroSession created = dao.create(entity);
        return mapper.toResponse(created);
    }

    public PomodoroSessionResponse update(Long id, PomodoroSessionRequest request) {
        PomodoroSession entity = mapper.toEntity(request);
        PomodoroSession updated = dao.update(id, entity);
        if (Objects.isNull(updated)) {
            throw new EntityNotFound(id, PomodoroSession.class.getSimpleName());
        }
        return mapper.toResponse(updated);
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}
