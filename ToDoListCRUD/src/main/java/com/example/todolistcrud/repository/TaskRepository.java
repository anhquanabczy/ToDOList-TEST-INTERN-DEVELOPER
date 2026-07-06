package com.example.ToDoListCRUD.repository;

import com.example.ToDoListCRUD.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Tìm kiếm theo tiêu đề
    List<Task> findByTitleContainingIgnoreCase(String title);

    // Lọc theo trạng thái hoàn thành
    List<Task> findByCompleted(boolean completed);

    // Kết hợp cả tìm kiếm và lọc
    List<Task> findByTitleContainingIgnoreCaseAndCompleted(String title, boolean completed);
    // Kiểm tra xem công việc đã tồn tại chưa
    boolean existsByTitleIgnoreCase(String title);
}