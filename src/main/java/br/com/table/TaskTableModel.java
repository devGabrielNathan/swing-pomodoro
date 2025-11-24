/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.table;

import br.com.dto.tarefa.TaskResponse;
import br.com.model.Task;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabrielnathan
 */
public class TaskTableModel extends AbstractTableModel {
    private final List<TaskResponse> tasks;
    private final String[] colunas = {
        "ID", "Título", "Descrição", "Categoria", 
        "Planejados", "Concluídos"
    };

    public TaskTableModel(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int getRowCount() {
        return tasks.size();
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
        TaskResponse t = tasks.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> t.getId();
            case 1 -> t.getTitle();
            case 2 -> t.getDescription();
            case 3 -> t.getCategory();
            case 4 -> t.getPlannedTotalPomodoros();
            case 5 -> t.getCompletedTotalPomodoros();
            default -> null;
        };
    }

    public TaskResponse get(int row) {
        return tasks.get(row);
    }

    public void add(TaskResponse t) {
        tasks.add(t);
        fireTableRowsInserted(tasks.size() - 1, tasks.size() - 1);
    }

    public void update(int row, TaskResponse t) {
        tasks.set(row, t);
        fireTableRowsUpdated(row, row);
    }

    public void remove(int row) {
        tasks.remove(row);
        fireTableRowsDeleted(row, row);
    }
}

