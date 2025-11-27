package br.com.mapper;

import br.com.dto.task.TaskRequest;
import br.com.dto.task.TaskResponse;
import br.com.model.Task;

public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        if (task == null) return null;
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCategoryId(task.getCategoryId());
        return response;
    }

    public Task toEntity(TaskRequest request) {
        if (request == null) {
            return null;
        }
        Task task = new Task();
        task.setId(request.getId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategoryId(request.getCategoryId());
        return task;
    }
}
