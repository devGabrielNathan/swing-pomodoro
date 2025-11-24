package br.com.controller;

import br.com.dto.pomodoroSession.PomodoroSessionRequest;
import br.com.dto.pomodoroSession.PomodoroSessionResponse;
import br.com.service.PomodoroSessionService;

import java.util.List;

// TODO: Implementar as validações necessárias
public class PomodoroSessionController {
    private final PomodoroSessionService pomodoroSessionService;
    
    public PomodoroSessionController() {
        this.pomodoroSessionService = new PomodoroSessionService();
    }

    public PomodoroSessionResponse findById(Long id) {
        return pomodoroSessionService.findById(id);
    }

    public List<PomodoroSessionResponse> findAll() {
        return pomodoroSessionService.findAll();
    }

    public PomodoroSessionResponse create(PomodoroSessionRequest request) {
        return pomodoroSessionService.create(request);
    }

    public PomodoroSessionResponse update(Long id, PomodoroSessionRequest request) {
        return pomodoroSessionService.update(id, request);
    }

    public void delete(Long id) {
        pomodoroSessionService.delete(id);
    }
}
