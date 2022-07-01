package com.example.webSocketTest.model;
import com.example.webSocketTest.entity.Todo;
import lombok.Data;
import java.util.List;

@Data
public class Response {
    private String status;
    private List<Todo> todos;
}
