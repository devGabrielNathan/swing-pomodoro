package br.com.controller;

import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.sessaoPomodoro.PomodoroSessionResponse;
import br.com.service.PomodoroSessionService;

import java.util.List;

public class PomodoroSessionController {
    private final PomodoroSessionService service;

    public PomodoroSessionController(PomodoroSessionService service) {
        this.service = service;
    }

    public PomodoroSessionResponse registrar(PomodoroSessionRequest request) {
        return service.create(request);
    }

    public PomodoroSessionResponse findById(Long id) {
        return service.findById(id);
    }

    public List<PomodoroSessionResponse> findAll() {
        return service.findAll();
    }

    public PomodoroSessionResponse update(Long id, PomodoroSessionRequest request) {
        return service.update(id, request);
    }

    public void delete(Long id) {
        service.delete(id);
    }
}
