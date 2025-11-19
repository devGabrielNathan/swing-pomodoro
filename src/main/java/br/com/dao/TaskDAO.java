package br.com.dao;

import br.com.exceptions.IntegrationException;
import br.com.model.Task;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO {
    private static final Logger logger = Logger.getLogger(TaskDAO.class.getName());
    private static final String FILE = "src/main/resources/tarefa.txt";
    private static final short FIELDS = 6;

    // TODO: Adicionar tratamento de exceção para quando a tarefa não for encontrada
    public Task findById(Long id) {
        List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
        return tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Task> findAll() {
        return FileHelper.readObjects(FILE, this::parseLine);
    }

    public Task create(Task request) {
        try {
            if (request == null) {
                logger.warning("Requisição vazia");
                return null;
            }
            long nextId = FileHelper.getNextId(FILE, FIELDS);
            request.setId(nextId);
            FileHelper.appendLine(FILE, formatTask(request));
            return findById(nextId);
        } catch (Exception e) {
            logger.log(Level.INFO, "Erro ao criar a tarefa");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    // TODO: Adicionar validação para verificar se a tarefa existe antes de atualizar
    public Task update(Long id, Task request) {
        List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
        boolean found = false;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task.getId().equals(id)) {
                Task updated = getTask(id, request, task);
                tasks.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) {
            return null;
        }
        FileHelper.writeObjects(FILE, tasks, this::formatTask, "src/main/resources/tarefa_temp.txt");
        return findById(id);
    }

    // TODO: Refatorar para usar a função findById e lançar exceção se a tarefa não for encontrada
    public void delete(Long id) {
        List<Task> tasks = FileHelper.readObjects(FILE, this::parseLine);
        Task task = tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        tasks.remove(task);
        FileHelper.writeObjects(FILE, tasks, this::formatTask, "src/main/resources/tarefa_temp.txt");
    }


    // TODO: Renomear o nome desse método
    private static Task getTask(Long id, Task request, Task t) {
        Task updated = new Task();
        updated.setId(id);
        updated.setTitle(request.getTitle() != null ? request.getTitle() : t.getTitle());
        updated.setDescription(request.getDescription() != null ? request.getDescription() : t.getDescription());
        updated.setCategory(request.getCategory() != null ? request.getCategory() : t.getCategory());
        updated.setPlannedTotalPomodoros(request.getPlannedTotalPomodoros() != null ? request.getPlannedTotalPomodoros() : t.getPlannedTotalPomodoros());
        updated.setCompletedTotalPomodoros(request.getCompletedTotalPomodoros() != null ? request.getCompletedTotalPomodoros() : t.getCompletedTotalPomodoros());
        return updated;
    }

    private Task parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        try {
            String[] parts = line.split(",");
            if (parts.length < FIELDS) {
                return null;
            }
            Long id = Long.parseLong(parts[0]);
            String title = parts[1];
            String description = parts[2];
            Long category = Long.parseLong(parts[3]);
            Long planned = Long.parseLong(parts[4]);
            Long completed = Long.parseLong(parts[5]);
            return new Task(id, title, description, category, planned, completed);
        } catch (Exception e) {
            logger.log(Level.INFO, "Falha ao analisar a linha da tarefa");
            logger.warning(e.getMessage());
            return null;
        }
    }

    private String formatTask(Task task) {
        if (task == null) {
            return "";
        }
        return task.getId() + "," + task.getTitle() + "," + task.getDescription() + "," + task.getCategory() + "," + task.getPlannedTotalPomodoros() + "," + task.getCompletedTotalPomodoros();
    }
}
