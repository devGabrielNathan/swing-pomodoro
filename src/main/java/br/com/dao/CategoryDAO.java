package br.com.dao;

import br.com.exceptions.EntityNotFound;
import br.com.exceptions.IntegrationException;
import br.com.model.Category;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.*;

public class CategoryDAO {
    private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());
    private static final String FILE = "src/main/resources/categoria.txt";
    private static final short FIELDS = 3;

    // TODO: Adicionar tratamento de exceção para quando a categoria não for encontrada
    public Category findById(Long id) {
        List<Category> categories = FileHelper.readObjects(FILE, this::parseLine);
        return categories.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Category> findAll() {
        return FileHelper.readObjects(FILE, this::parseLine);
    }

    public Category create(Category request) {
        try {
            if (request == null) {
                logger.warning("Requisição vazia");
                return null;
            }
            Long nextId = FileHelper.getNextId(FILE, FIELDS);
            request.setId(nextId);
            FileHelper.appendLine(FILE, formatCategory(request));
            return findById(nextId);
        } catch (Exception e) {
            logger.log(Level.INFO, "Erro ao criar a categoria");
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    // TODO: Adicionar validação para verificar se a categoria existe antes de atualizar
    public Category update(Long id, Category request) {
        List<Category> categories = FileHelper.readObjects(FILE, this::parseLine);
        boolean found = false;

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);

            if (category.getId().equals(id)) {
                Category updated = getCategory(id, request, category);
                categories.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) {
            logger.log(Level.INFO, "Não foi possível encontrar a categoria com id {0}", id);
            throw new EntityNotFound(id, Category.class.getSimpleName());
        }
        FileHelper.writeObjects(FILE, categories, this::formatCategory, "src/main/resources/categoria_temp.txt");
        return findById(id);
    }

    // TODO: Refatorar para usar a função findById e lançar exceção se a categoria não for encontrada
    public void delete(Long id) {
        List<Category> categories = FileHelper.readObjects(FILE, this::parseLine);
        Category category = categories.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        categories.remove(category);
        FileHelper.writeObjects(FILE, categories, this::formatCategory, "src/main/resources/categoria_temp.txt");
    }

    public Boolean existsCategory(Long id) {
        Category category = findById(id);
        return Objects.nonNull(category);
    }

    // TODO: Renomear o nome desse método
    private static Category getCategory(Long id, Category request, Category c) {
        Category updated = new Category();
        updated.setId(id);
        updated.setName(request.getName() != null ? request.getName() : c.getName());
        updated.setDescription(request.getDescription() != null ? request.getDescription() : c.getDescription());
        return updated;
    }

    private Category parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        try {
            String[] parts = line.split("#");

            if (parts.length < FIELDS) {
                return null;
            }
            Long id = Long.parseLong(parts[0]);
            String name = parts[1];
            String description = parts[2];
            return new Category(id, name, description);
        } catch (Exception e) {
            logger.log(Level.INFO, "Falha ao analisar a linha da categoria");
            logger.warning(e.getMessage());
            return null;
        }
    }

    private String formatCategory(Category category) {
        return category.getId() + "#" +
               category.getName() + "#" +
               category.getDescription();
    }
}
