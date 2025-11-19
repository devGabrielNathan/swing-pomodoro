package br.com.service;

import br.com.dao.CategoryDAO;
import br.com.dto.categoria.CategoryRequest;
import br.com.dto.categoria.CategoryResponse;
import br.com.exceptions.EntityNotFound;
import br.com.mapper.CategoryMapper;
import br.com.model.Category;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public record CategoryService(CategoryDAO categoryDAO, CategoryMapper categoryMapper) {
    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());
    private static final short FIELDS = 3;

    public CategoryResponse findById(Long id) {
        Category category = categoryDAO.findById(id);
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryDAO.findAll();
        return categories.stream().map(categoryMapper::toResponse).toList();
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        categoryDAO.create(category);

        return categoryMapper.toResponse(category);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Boolean existsCategory = categoryDAO.existsCategory(id);
        if (!existsCategory) {
            throw new EntityNotFound(id, Category.class.getSimpleName());
        }

        Category category = categoryMapper.toEntity(request);
        Category updated = categoryDAO.update(id, category);

        return categoryMapper.toResponse(updated);
    }

    public void delete(Long id) {
        Category category = categoryDAO.findById(id);

        if (Objects.isNull(category.getId())) {
            throw new EntityNotFound(id, Category.class.getSimpleName());
        }
        categoryDAO.delete(id);
    }
}
