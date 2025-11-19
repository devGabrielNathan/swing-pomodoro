package br.com.mapper;

import br.com.dto.tarefa.TaskRequest;
import br.com.dto.tarefa.TaskResponse;
import br.com.model.Task;

public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        if (task == null) return null;
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCategory(task.getCategory());
        response.setPlannedTotalPomodoros(task.getPlannedTotalPomodoros());
        response.setCompletedTotalPomodoros(task.getCompletedTotalPomodoros());
        return response;
    }

    public Task toEntity(TaskRequest request) {
        if (request == null) {
            return null;
        }
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategory(request.getCategory());
        task.setPlannedTotalPomodoros(request.getPlannedTotalPomodoros());
        task.setCompletedTotalPomodoros(request.getCompletedTotalPomodoros());
        return task;
    }
}
