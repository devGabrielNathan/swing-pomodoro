package br.com.controller;

import br.com.dto.tarefa.TaskRequest;
import br.com.dto.tarefa.TaskResponse;
import br.com.service.TaskService;

import java.util.List;

// TODO: Implementar as validações necessárias
public record TaskController(TaskService taskService) {

    public TaskResponse findById(Long id) {
        return taskService.findById(id);
    }

    public List<TaskResponse> findAll() {
        return taskService.findAll();
    }

    public TaskResponse create(TaskRequest request) {
        return taskService.create(request);
    }

    public TaskResponse update(Long id, TaskRequest request) {
        return taskService.update(id, request);
    }

    public void delete(Long id) {
        taskService.delete(id);
    }
}
