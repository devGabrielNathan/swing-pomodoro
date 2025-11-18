package br.com;

import br.com.controller.CategoryController;
import br.com.controller.PomodoroSessionController;
import br.com.controller.TaskController;
import br.com.dao.CategoryDAO;
import br.com.dao.PomodoroSessionDAO;
import br.com.dao.TaskDAO;
import br.com.dto.categoria.CategoryRequest;
import br.com.dto.sessaoPomodoro.PomodoroSessionRequest;
import br.com.dto.tarefa.TaskRequest;
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
        CategoryDAO categoryDAO = new CategoryDAO();
        CategoryMapper categoryMapper = new CategoryMapper();
        CategoryService categoryService = new CategoryService(categoryDAO, categoryMapper);
        CategoryController categoryController = new CategoryController(categoryService);

        TaskDAO taskDAO = new TaskDAO();
        TaskMapper taskMapper = new TaskMapper();
        TaskService taskService = new TaskService(taskDAO, taskMapper);
        TaskController taskController = new TaskController(taskService);

        PomodoroSessionDAO pomodoroSessionDAO = new PomodoroSessionDAO();
        PomodoroSessionMapper pomodoroSessionMapper = new PomodoroSessionMapper();
        PomodoroSessionService pomodoroSessionService = new PomodoroSessionService(pomodoroSessionDAO, pomodoroSessionMapper);
        PomodoroSessionController pomodoroSessionController = new PomodoroSessionController(pomodoroSessionService);


        System.out.println(categoryController.findById(1L).getName());;
//        taskController.findById(1L);
        System.out.println(pomodoroSessionController.findById(1L).getDuration());

        categoryController.findAll();
//        taskController.findAll();
        pomodoroSessionController.findAll();

//        categoryController.delete(1L);
//        taskController.delete(1L);
//        pomodoroSessionController.delete(1L);

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("New Category");
        categoryController.create(categoryRequest);

//        TaskRequest taskRequest = new TaskRequest();
//        taskRequest.setTitle("New Task");
//        taskRequest.setDescription("Task Description");
//        taskRequest.setCategory(1L);
//        taskRequest.setPlannedTotalPomodoros(1L);
//        taskRequest.setCompletedTotalPomodoros(1L);
//        taskController.create(taskRequest);

        PomodoroSessionRequest pomodoroSessionRequest = new PomodoroSessionRequest();
        pomodoroSessionRequest.setTarefaId(1L);
        pomodoroSessionRequest.setDuration(25L);
        pomodoroSessionController.registrar(pomodoroSessionRequest);




//        SwingUtilities.invokeLater(() -> {
//            TelaPrincipal tela = new TelaPrincipal(categoryController, taskController, pomodoroSessionController);
//            tela.setVisible(true);
//        });
    }
}
