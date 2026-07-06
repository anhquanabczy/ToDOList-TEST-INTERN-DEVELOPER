package com.example.ToDoListCRUD.controller;

import com.example.ToDoListCRUD.model.Task;
import com.example.ToDoListCRUD.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetTasks_Returns200AndJsonArray() throws Exception {
        // 1. Chuẩn bị dữ liệu giả
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test API Controller");
        task.setCompleted(false);

        when(taskService.getAllTasks(null, null)).thenReturn(Arrays.asList(task));

        // 2. Thực thi gọi API và 3. Kiểm tra kết quả
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test API Controller"));
    }
}