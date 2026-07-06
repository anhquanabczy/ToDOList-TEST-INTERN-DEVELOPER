package com.example.ToDoListCRUD;

import com.example.ToDoListCRUD.model.Task;
import com.example.ToDoListCRUD.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component 
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem trong Database đã có dữ liệu chưa
        // Nếu count == 0 (tức là database trống), thì mới thêm dữ liệu mẫu
        if (taskRepository.count() == 0) {

            Task task1 = new Task();
            task1.setTitle("ăn");
            task1.setCompleted(true);

            Task task2 = new Task();
            task2.setTitle("uống");
            task2.setCompleted(false);

            Task task3 = new Task();
            task3.setTitle("ngủ");
            task3.setCompleted(true);

            Task task4 = new Task();
            task4.setTitle("nghỉ");
            task4.setCompleted(false);

            // Lưu toàn bộ vào Database cùng một lúc
            List<Task> initialTasks = Arrays.asList(task1, task2, task3, task4);
            taskRepository.saveAll(initialTasks);

        } else {
            System.out.println("Database đã có sẵn dữ liệu");
        }
    }
}