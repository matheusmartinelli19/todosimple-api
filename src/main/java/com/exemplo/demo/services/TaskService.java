package com.exemplo.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exemplo.demo.models.Task;
import com.exemplo.demo.models.User;
import com.exemplo.demo.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;

    public TaskService(UserService userService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
            ));
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());

        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;

    }

     
    @Transactional
    public Task update(Task obj) {
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir a Task, pois há entidades relacionadas!");
        }
    }

}
