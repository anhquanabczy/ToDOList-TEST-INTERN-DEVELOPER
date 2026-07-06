package com.example.ToDoListCRUD.service;

import com.example.ToDoListCRUD.model.Task;
import com.example.ToDoListCRUD.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(String search, Boolean completed) {
        if (search != null && completed != null) {
            return taskRepository.findByTitleContainingIgnoreCaseAndCompleted(search, completed);
        } else if (search != null) {
            return taskRepository.findByTitleContainingIgnoreCase(search);
        } else if (completed != null) {
            return taskRepository.findByCompleted(completed);
        }
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        // Cắt khoảng trắng 2 đầu để tránh trường hợp " học " và "học"
        String taskTitle = task.getTitle().trim();

        // Kiểm tra trùng lặp
        if (taskRepository.existsByTitleIgnoreCase(taskTitle)) {
            throw new IllegalArgumentException("Công việc này đã tồn tại trong danh sách!");
        }

        task.setTitle(taskTitle);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc với ID: " + id));
        task.setTitle(taskDetails.getTitle());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task toggleTaskStatus(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc với ID: " + id));
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

}