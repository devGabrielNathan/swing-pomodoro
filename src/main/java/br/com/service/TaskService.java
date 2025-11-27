package br.com.service;

import br.com.dao.TaskDAO;
import br.com.dto.task.TaskRequest;
import br.com.dto.task.TaskResponse;
import br.com.exceptions.EntityNotFound;
import br.com.mapper.TaskMapper;
import br.com.model.Task;

import java.util.List;

public class TaskService {
    private final TaskDAO taskDAO;
    private final TaskMapper taskMapper;
    
    public TaskService() {
        this.taskDAO = new TaskDAO();
        this.taskMapper = new TaskMapper();
    }
    public TaskResponse findById(Long id) {
        Task task = taskDAO.findById(id);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponse> findAll() {
        List<Task> tasks = taskDAO.findAll();
        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    public TaskResponse create(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        Task created = taskDAO.create(task);
        return taskMapper.toResponse(created);
    }

    public TaskResponse update(Long id, TaskRequest request) {
        Task entity = taskMapper.toEntity(request);
        Task updated = taskDAO.update(id, entity);
        if (updated == null) {
            throw new EntityNotFound(id, Task.class.getSimpleName());
        }
        return taskMapper.toResponse(updated);
    }

    public void delete(Long id) {
        taskDAO.delete(id);
    }
}
