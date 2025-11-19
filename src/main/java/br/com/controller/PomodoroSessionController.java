package br.com.controller;

import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.sessaoPomodoro.PomodoroSessionResponse;
import br.com.service.PomodoroSessionService;

import java.util.List;

// TODO: Implementar as validações necessárias
public record PomodoroSessionController(PomodoroSessionService pomodoroSessionService) {

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
