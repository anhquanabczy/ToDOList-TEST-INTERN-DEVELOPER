package com.example.ToDoListCRUD.service;

import com.example.ToDoListCRUD.model.Task;
import com.example.ToDoListCRUD.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private com.example.ToDoListCRUD.service.TaskService taskService;

    @Test
    public void testCreateTask() {
        Task mockTask = new Task();
        mockTask.setId(1L);
        mockTask.setTitle("Unit Test");
        mockTask.setCompleted(false);

        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task taskToSave = new Task();
        taskToSave.setTitle("Unit Test");
        Task savedTask = taskService.createTask(taskToSave);

        assertNotNull(savedTask);
        assertEquals("Unit Test", savedTask.getTitle());
        assertEquals(1L, savedTask.getId());
    }

    @Test
    public void testToggleTaskStatus() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setCompleted(false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task toggledTask = taskService.toggleTaskStatus(1L);

        assertTrue(toggledTask.isCompleted());
    }
}