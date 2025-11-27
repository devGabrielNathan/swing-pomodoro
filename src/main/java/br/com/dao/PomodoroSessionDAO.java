package br.com.dao;

import br.com.exceptions.IntegrationException;
import br.com.model.PomodoroSession;
import br.com.utils.Status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomodoroSessionDAO {
    private static final Logger logger = Logger.getLogger(PomodoroSessionDAO.class.getName());
    private static final String FILE = "src/main/resources/sessao_pomodoro.txt";
    private static final short FIELDS = 6;

    // TODO: Adicionar tratamento de exceção para quando a Sessão do Pomodoro não for encontrada
    public PomodoroSession findById(Long id) {
        List<PomodoroSession> pomodoroSessions = FileHelper.readObjects(FILE, this::parseLine);
        return pomodoroSessions.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public List<PomodoroSession> findAll() {
        return FileHelper.readObjects(FILE, this::parseLine);
    }

    public PomodoroSession create(PomodoroSession request) {
        try {
            if (request == null) {
                logger.warning("Request is null.");
                return null;
            }
            long nextId = FileHelper.getNextId(FILE, FIELDS);
            request.setId(nextId);
            FileHelper.appendLine(FILE, formatPomodoroSession(request));
            return findById(nextId);
        } catch (Exception e) {
            logger.log(Level.INFO, "Erro ao criar a Sessão do Pomodoro");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    // TODO: Adicionar validação para verificar se a Sessão do Pomodoro existe antes de atualizar
    public PomodoroSession update(Long id, PomodoroSession request) {
        List<PomodoroSession> pomodoroSessions = FileHelper.readObjects(FILE, this::parseLine);
        boolean found = false;

        for (int i = 0; i < pomodoroSessions.size(); i++) {
            PomodoroSession pomodoroSession = pomodoroSessions.get(i);
            if (pomodoroSession == null) {
                continue;
            }
            if (pomodoroSession.getId().equals(id)) {
                PomodoroSession updated = getPomodoroSession(id, request, pomodoroSession);
                pomodoroSessions.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) {
            return null;
        }
        FileHelper.writeObjects(FILE, pomodoroSessions, this::formatPomodoroSession, "src/main/resources/sessao_pomodoro_temp.txt");
        return findById(id);
    }

    // TODO: Refatorar para usar a função findById e lançar exceção se a Sessão do Pomodoro não for encontrada
    public void delete(Long id) {
        List<PomodoroSession> pomodoroSessions = FileHelper.readObjects(FILE, this::parseLine);
        PomodoroSession pomodoroSession = pomodoroSessions.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        pomodoroSessions.remove(pomodoroSession);
        FileHelper.writeObjects(FILE, pomodoroSessions, this::formatPomodoroSession, "src/main/resources/sessao_pomodoro_temp.txt");
    }


    private static PomodoroSession getPomodoroSession(Long id, PomodoroSession request, PomodoroSession pomodoroSession) {
        PomodoroSession updated = new PomodoroSession();
        updated.setId(id);
        updated.setTarefaId(request.getTarefaId() != null ? request.getTarefaId() : pomodoroSession.getTarefaId());
        updated.setDuration(request.getDuration() != null ? request.getDuration() : pomodoroSession.getDuration());
        updated.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : pomodoroSession.getCreatedAt());
        updated.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : new Date());
        updated.setStatus(request.getStatus() != null ? request.getStatus() : pomodoroSession.getStatus());
        return updated;
    }

    private PomodoroSession parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        try {
            String[] parts = line.split("#");

            if (parts.length < FIELDS) {
                return null;
            }

            Long currentId = Long.parseLong(parts[0]);
            Long tarefaId  = Long.parseLong(parts[1]);
            Long duration  = Long.parseLong(parts[2]);
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createdAt = formatDate.parse(parts[3]);
            Date updatedAt = formatDate.parse(parts[4]);
            Status status = Status.valueOf(parts[5]);
            return new PomodoroSession(currentId, tarefaId, duration, createdAt, updatedAt, status);
        } catch (Exception e) {
            logger.log(Level.INFO, "Falha ao analisar a linha da Sessão do Pomodoro");
            logger.warning(e.getMessage());
            return null;
        }
    }

    private String formatPomodoroSession(PomodoroSession pomodoroSession) {
        long created, updated;
        String status;

        if (pomodoroSession == null) {
            return "";
        }

        if (Objects.nonNull(pomodoroSession.getId())) {
            created = pomodoroSession.getCreatedAt().getTime();
        } else {
            created = new Date().getTime();
        }

        if (Objects.nonNull(pomodoroSession.getUpdatedAt())) {
            updated = pomodoroSession.getUpdatedAt().getTime();
        } else {
            updated = new Date().getTime();
        }

        if (Objects.nonNull(pomodoroSession.getStatus())) {
            status = pomodoroSession.getStatus().name();
        } else {
            status = Status.IN_PROGRESS.name();
        }

        return pomodoroSession.getId() + "#" + pomodoroSession.getTarefaId() + "#" + pomodoroSession.getDuration() + "#" + created + "#" + updated + "#" + status;
    }

}
