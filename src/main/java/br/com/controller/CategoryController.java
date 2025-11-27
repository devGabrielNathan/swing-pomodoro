package br.com.controller;

import br.com.dto.category.CategoryRequest;
import br.com.dto.category.CategoryResponse;
import br.com.service.CategoryService;

import java.util.List;

// TODO: Implementar as validações necessárias
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public CategoryResponse findById(Long id) {
        return categoryService.findById(id);
    }

    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    public CategoryResponse create(CategoryRequest request) {
        return categoryService.create(request);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        return categoryService.update(id, request);
    }

    public void delete(Long id) {
        categoryService.delete(id);
    }
}
