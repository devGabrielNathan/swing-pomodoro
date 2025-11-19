package br.com;

import br.com.controller.CategoryController;
import br.com.controller.PomodoroSessionController;
import br.com.controller.TaskController;
import br.com.dao.CategoryDAO;
import br.com.dao.PomodoroSessionDAO;
import br.com.dao.TaskDAO;
import br.com.dto.categoria.CategoryRequest;
import br.com.dto.categoria.CategoryResponse;
import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.tarefa.TaskRequest;
import br.com.dto.tarefa.TaskResponse;
import br.com.mapper.CategoryMapper;
import br.com.mapper.PomodoroSessionMapper;
import br.com.mapper.TaskMapper;
import br.com.service.CategoryService;
import br.com.service.PomodoroSessionService;
import br.com.service.TaskService;
import br.com.view.PomodoroSession;
import br.com.view.TelaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== Categoria Testes =====");
        CategoryDAO categoryDAO = new CategoryDAO();
        CategoryMapper categoryMapper = new CategoryMapper();
        CategoryService categoryService = new CategoryService(categoryDAO, categoryMapper);
        CategoryController categoryController = new CategoryController(categoryService);

        categoryController.findById(1L);

        for (int i = 0; i < categoryController.findAll().size(); i++) {
            System.out.printf(String.format("%d - Nome: %s Descrição: %s", i, categoryController.findAll().get(i).getName(), categoryController.findAll().get(i).getDescription()));
            System.out.println("\n");
        }

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setName("Outra Categoria");
        categoryRequest.setDescription("Descrição para outra categoria.");
        CategoryResponse responseCategoryCreate = categoryController.create(categoryRequest);
        System.out.printf("Id da categoria criada: %d\n\n", responseCategoryCreate.getId());

        categoryRequest.setName("Mudando Nome");
        categoryRequest.setDescription("Mudando Descrição");
        CategoryResponse responseCategoryUpdate = categoryController.update(1L, categoryRequest);
        System.out.printf(String.format("Id da categoria atualizada: %d\n\n", responseCategoryUpdate.getId()));

        categoryController.delete(1L);

        System.out.println("===== Tarefas Testes =====");
        TaskDAO taskDAO = new TaskDAO();
        TaskMapper taskMapper = new TaskMapper();
        TaskService taskService = new TaskService(taskDAO, taskMapper);
        TaskController taskController = new TaskController(taskService);

        taskController.findById(1L);

        for (int i = 0; i <  taskController.findAll().size(); i++) {
            System.out.printf(String.format("%d - Title: %s Description: %s", i, taskController.findAll().get(i).getTitle(), taskController.findAll().get(i).getDescription()));
            System.out.println("\n");
        }

        TaskRequest taskRequest = new TaskRequest();

        taskRequest.setTitle("Outra Tarefa");
        taskRequest.setDescription("Descrição para outra tarefa.");
        taskRequest.setCategory(1L);
        taskRequest.setPlannedTotalPomodoros(4L);
        taskRequest.setCompletedTotalPomodoros(0L);
        TaskResponse responseTaskCreate = taskController.create(taskRequest);
        System.out.printf("Id da categoria criada: %d\n\n", responseTaskCreate.getId());

        taskController.delete(1L);


        System.out.println("===== Sessão de Pomodoros Testes =====");
//        SwingUtilities.invokeLater(() -> {
//            TelaPrincipal tela = new TelaPrincipal(categoryController, taskController, pomodoroSessionController);
//            tela.setVisible(true);
//        });
    }
}
