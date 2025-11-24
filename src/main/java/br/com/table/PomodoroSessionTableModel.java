/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.table;

import br.com.dto.pomodoroSession.PomodoroSessionResponse;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabrielnathan
 */
public class PomodoroSessionTableModel extends AbstractTableModel {
    private final List<PomodoroSessionResponse> sessions;
    private final String[] colunas = {
        "ID", "Tarefa", "Duração", 
        "Criado em", "Atualizado em", "Status"
    };

    public PomodoroSessionTableModel(List<PomodoroSessionResponse> sessions) {
        this.sessions = sessions;
    }

    @Override
    public int getRowCount() {
        return sessions.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PomodoroSessionResponse s = sessions.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getId();
            case 1 -> s.getTarefaId();
            case 2 -> s.getDuration();
            case 3 -> s.getCreatedAt();
            case 4 -> s.getUpdatedAt();
            case 5 -> s.getStatus();
            default -> null;
        };
    }

    public PomodoroSessionResponse get(int row) {
        return sessions.get(row);
    }

    public void add(PomodoroSessionResponse s) {
        sessions.add(s);
        fireTableRowsInserted(sessions.size() - 1, sessions.size() - 1);
    }

    public void update(int row, PomodoroSessionResponse s) {
        sessions.set(row, s);
        fireTableRowsUpdated(row, row);
    }

    public void remove(int row) {
        sessions.remove(row);
        fireTableRowsDeleted(row, row);
    }
}

