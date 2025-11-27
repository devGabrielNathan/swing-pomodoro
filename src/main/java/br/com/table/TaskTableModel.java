/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.table;

import br.com.controller.CategoryController;
import br.com.dto.category.CategoryResponse;
import br.com.dto.task.TaskResponse;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabrielnathan
 */
public class TaskTableModel extends AbstractTableModel {

    private final CategoryController categoryController;
    private final List<TaskResponse> tasks;
    private final String[] colunas = {
        "ID", "Título", "Descrição", "Categoria"
    };

    public TaskTableModel(List<TaskResponse> tasks) {
        this.categoryController = new CategoryController();
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
        TaskResponse task = tasks.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return task.getId();
            case 1:
                return task.getTitle();
            case 2:
                return task.getDescription() != null ? task.getDescription() : "";
            case 3: // Categoria
                Long catId = task.getCategoryId();
                if (catId == null) {
                    return "Sem categoria";
                }
                try {
                    CategoryResponse cat = categoryController.findById(catId);
                    return cat != null ? cat.getName() : "Categoria removida";
                } catch (Exception e) {
                    return "Erro ao carregar";
                }
            default:
                return null;
        }
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

