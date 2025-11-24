/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.table;

import br.com.dto.categoria.CategoryResponse;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gabrielnathan
 */
public class CategoryTableModel extends AbstractTableModel {

    private final List<CategoryResponse> categories;
    private final String[] colunas = {"ID", "Nome", "Descrição"};

    public CategoryTableModel(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    @Override
    public int getRowCount() {
        return categories.size();
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
        CategoryResponse c = categories.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getId();
            case 1 -> c.getName();
            case 2 -> c.getDescription();
            default -> null;
        };
    }

    public CategoryResponse get(int row) {
        return categories.get(row);
    }

    public void add(CategoryResponse c) {
        categories.add(c);
        fireTableRowsInserted(categories.size() - 1, categories.size() - 1);
    }

    public void update(int row, CategoryResponse c) {
        categories.set(row, c);
        fireTableRowsUpdated(row, row);
    }

    public void remove(int row) {
        categories.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
