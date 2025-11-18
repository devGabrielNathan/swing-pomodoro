package br.com.dao;

import br.com.exceptions.CustomFileNotFoundException;
import br.com.exceptions.IntegrationException;
import br.com.model.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO {
    private static final Logger logger = Logger.getLogger(TaskDAO.class.getName());
    private static final String FILE = "src/main/resources/tarefa.txt";
    private static final short FIELDS = 6;

    private Task parseLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        try {
            String[] parts = line.split(",");
            if (parts.length < FIELDS) return null;
            Long id = Long.parseLong(parts[0]);
            String title = parts[1];
            String description = parts[2];
            Long category = Long.parseLong(parts[3]);
            Long planned = Long.parseLong(parts[4]);
            Long completed = Long.parseLong(parts[5]);
            return new Task(id, title, description, category, planned, completed);
        } catch (Exception e) {
            logger.warning("Failed to parse task line: " + line + " -> " + e.getMessage());
            return null;
        }
    }

    private String formatTask(Task task) {
        if (task == null) return "";
        return task.getId() + "," + task.getTitle() + "," + task.getDescription() + "," + task.getCategory() + "," + task.getPlannedTotalPomodoros() + "," + task.getCompletedTotalPomodoros();
    }

    public Task findById(Long id) {
        try {
            List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
            return tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        } catch (FileNotFoundException e) {
            throw new CustomFileNotFoundException();
        } catch (IOException e) {
            throw new IntegrationException();
        }
    }

    public List<Task> findAll() {
        try {
            List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
            return tasks == null ? List.of() : tasks;
        } catch (FileNotFoundException e) {
            throw new CustomFileNotFoundException();
        } catch (IOException e) {
            throw new IntegrationException();
        }
    }

    public Task create(Task request) {
        try {
            if (request == null) return null;
            long nextId = FileHelper.getNextId(FILE, FIELDS);
            request.setId(nextId);
            FileHelper.appendLine(FILE, formatTask(request));
            return findById(nextId);
        } catch (Exception e) {
            logger.log(Level.INFO, "Error creating task.");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    public void delete(Long id) {
        try {
            List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
            List<Task> out = new ArrayList<>();
            for (Task t : tasks) {
                if (t == null) continue;
                if (!t.getId().equals(id)) out.add(t);
            }
            FileHelper.writeObjectsAtomic(FILE, out, this::formatTask, "src/main/resources/tarefa_temp.txt");
        } catch (FileNotFoundException e) {
            throw new CustomFileNotFoundException();
        } catch (IOException e) {
            throw new IntegrationException();
        }
    }

    public Task update(Long id, Task request) {
        try {
            List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
            boolean found = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if (t == null) continue;
                if (t.getId().equals(id)) {
                    Task updated = new Task();
                    updated.setId(id);
                    updated.setTitle(request.getTitle() != null ? request.getTitle() : t.getTitle());
                    updated.setDescription(request.getDescription() != null ? request.getDescription() : t.getDescription());
                    updated.setCategory(request.getCategory() != null ? request.getCategory() : t.getCategory());
                    updated.setPlannedTotalPomodoros(request.getPlannedTotalPomodoros() != null ? request.getPlannedTotalPomodoros() : t.getPlannedTotalPomodoros());
                    updated.setCompletedTotalPomodoros(request.getCompletedTotalPomodoros() != null ? request.getCompletedTotalPomodoros() : t.getCompletedTotalPomodoros());
                    tasks.set(i, updated);
                    found = true;
                    break;
                }
            }
            if (!found) return null;
            FileHelper.writeObjectsAtomic(FILE, tasks, this::formatTask, "src/main/resources/tarefa_temp.txt");
            return findById(id);
        } catch (FileNotFoundException e) {
            throw new CustomFileNotFoundException();
        } catch (IOException e) {
            throw new IntegrationException();
        }
    }
}
