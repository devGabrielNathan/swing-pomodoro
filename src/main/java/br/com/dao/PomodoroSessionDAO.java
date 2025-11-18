package br.com.dao;

import br.com.exceptions.IntegrationException;
import br.com.model.PomodoroSession;
import br.com.utils.Status;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PomodoroSessionDAO {
    private static final Logger logger = Logger.getLogger(PomodoroSessionDAO.class.getName());
    private static final String FILE = "src/main/resources/sessao_pomodoro.txt";
    private static final short FIELDS = 6;

    private PomodoroSession parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        try {
            String[] parts = line.split(",");
            if (parts.length < FIELDS) {
                return null;
            }

            Long currentId = Long.parseLong(parts[0]);
            Long tarefaId  = Long.parseLong(parts[1]);
            Long duration  = Long.parseLong(parts[2]);
            Date createdAt = new Date(Long.parseLong(parts[3]));
            Date updatedAt = new Date(Long.parseLong(parts[4]));
            Status status = Status.valueOf(parts[5]);
            return new PomodoroSession(currentId, tarefaId, duration, createdAt, updatedAt, status);
        } catch (Exception e) {
            logger.warning("Failed to parse line: " + line + " -> " + e.getMessage());
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

        return pomodoroSession.getId() + "," + pomodoroSession.getTarefaId() + "," + pomodoroSession.getDuration() + "," + created + "," + updated + "," + status;
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
            return request;
        } catch (Exception e) {
            logger.log(Level.INFO, "Error creating pomodoro pomodoroSession.");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    public PomodoroSession findById(Long id) {
        try {
            List<PomodoroSession> sessions = FileHelper.readObjects(FILE, this::parseLine);
            return sessions.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            logger.log(Level.INFO, "Error finding pomodoro session by ID: {0}.", id);
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    public List<PomodoroSession> findAll() {
        try {
            List<PomodoroSession> sessions = FileHelper.readObjects(FILE, this::parseLine);
            if (sessions.isEmpty()) return List.of();
            return sessions;
        } catch (FileNotFoundException e) {
            return List.of();
        } catch (Exception e) {
            logger.log(Level.INFO, "Error finding all pomodoro sessions.");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    public PomodoroSession update(Long id, PomodoroSession request) {
        try {
            List<PomodoroSession> sessions = FileHelper.readObjects(FILE, this::parseLine);
            boolean found = false;
            for (int i = 0; i < sessions.size(); i++) {
                PomodoroSession pomodoroSession = sessions.get(i);
                if (pomodoroSession == null) {
                    continue;
                }
                if (pomodoroSession.getId().equals(id)) {
                    PomodoroSession updated = getPomodoroSession(id, request, pomodoroSession);
                    sessions.set(i, updated);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return null;
            }
            FileHelper.writeObjectsAtomic(FILE, sessions, this::formatPomodoroSession, "src/main/resources/sessao_pomodoro_temp.txt");
            return findById(id);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            logger.log(Level.INFO, "Error updating pomodoro session with ID: {0}.", id);
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
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

    public void delete(Long id) {
        try {
            List<PomodoroSession> sessions = FileHelper.readObjects(FILE, this::parseLine);
            List<PomodoroSession> out = new ArrayList<>();
            for (PomodoroSession s : sessions) {
                if (s == null) continue;
                if (!s.getId().equals(id)) out.add(s);
            }
            FileHelper.writeObjectsAtomic(FILE, out, this::formatPomodoroSession, "src/main/resources/sessao_pomodoro_temp.txt");
            logger.log(Level.INFO, "Pomodoro session with ID: {0} successfully deleted.", id);
        } catch (FileNotFoundException e) {
            // nothing to do
        } catch (Exception e) {
            logger.log(Level.INFO, "Error deleting pomodoro session with ID: {0}.", id);
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }
}
