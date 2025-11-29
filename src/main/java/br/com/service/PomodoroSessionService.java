package br.com.service;

import br.com.dao.PomodoroSessionDAO;
import br.com.dto.pomodoroSession.PomodoroSessionRequest;
import br.com.dto.pomodoroSession.PomodoroSessionResponse;
import br.com.exceptions.EntityNotFound;
import br.com.mapper.PomodoroSessionMapper;
import br.com.model.PomodoroSession;

import java.util.List;
import java.util.Objects;

public class PomodoroSessionService {
    private final PomodoroSessionDAO pomodoroSessionDAO;
    private final PomodoroSessionMapper pomodoroSessionMapper;
    
    public PomodoroSessionService() {
        this.pomodoroSessionDAO = new PomodoroSessionDAO();
        this.pomodoroSessionMapper = new PomodoroSessionMapper();
    }

    public PomodoroSessionResponse findById(Long id) {
        PomodoroSession session = pomodoroSessionDAO.findById(id);
        return pomodoroSessionMapper.toResponse(session);
    }

    public List<PomodoroSessionResponse> findAll() {
        List<PomodoroSession> sessions = pomodoroSessionDAO.findAll();
        return sessions.stream()
                .map(pomodoroSessionMapper::toResponse)
                .filter(Objects::nonNull)
                .toList();
    }

    public PomodoroSessionResponse create(PomodoroSessionRequest request) {
        PomodoroSession entity = pomodoroSessionMapper.toEntity(request);
        PomodoroSession created = pomodoroSessionDAO.create(entity);
        return pomodoroSessionMapper.toResponse(created);
    }

    public PomodoroSessionResponse update(Long id, PomodoroSessionRequest request) {
        PomodoroSession entity = pomodoroSessionMapper.toEntity(request);
        PomodoroSession updated = pomodoroSessionDAO.update(id, entity);
        if (Objects.isNull(updated)) {
            throw new EntityNotFound(id, PomodoroSession.class.getSimpleName());
        }
        return pomodoroSessionMapper.toResponse(updated);
    }

    public void delete(Long id) {
        pomodoroSessionDAO.delete(id);
    }
}
