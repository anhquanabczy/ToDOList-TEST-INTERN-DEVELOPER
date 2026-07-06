package com.example.ToDoListCRUD.controller;

import com.example.ToDoListCRUD.model.Task;
import com.example.ToDoListCRUD.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // 1. Hiển thị danh sách, tìm kiếm và lọc
    @GetMapping
    public List<Task> getTasks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean completed) {
        return taskService.getAllTasks(search, completed);
    }

    // 2. Thêm công việc mới
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask); // Trả về mã 200 OK nếu thành công
        } catch (IllegalArgumentException e) {
            // Trả về mã 400 Bad Request nếu bị trùng lặp
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Chỉnh sửa công việc
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails));
    }

    // 4. Xóa công việc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Đánh dấu hoàn thành/chưa hoàn thành
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskStatus(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleTaskStatus(id));
    }
}