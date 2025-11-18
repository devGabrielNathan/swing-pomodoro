package br.com.controller;

import br.com.dto.categoria.CategoryRequest;
import br.com.dto.categoria.CategoryResponse;
import br.com.service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
