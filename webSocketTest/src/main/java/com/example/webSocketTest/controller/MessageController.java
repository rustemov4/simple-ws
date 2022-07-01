package com.example.webSocketTest.controller;

import com.example.webSocketTest.model.MessageModel;
import com.example.webSocketTest.model.Response;
import com.example.webSocketTest.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;



@Controller
public class MessageController {
    @Autowired
    TodoService todoService;

    @MessageMapping("/message")
    @SendTo("/topic/todos")
    public Response add(@Payload MessageModel model) {
        Response response = new Response();
        if (model.getCommand().equals("addLog")) {
            todoService.add(model);
            response.setStatus("OK");
            response.setTodos(todoService.getAllTodos());
            return response;

        } else if (model.getCommand().equals("logs")) {
            response.setStatus("OK");
            response.setTodos(todoService.getAllTodos());
            return response;
        } else {
            response.setStatus("BAD_REQUEST");
            response.setTodos(null);
            return response;
        }
    }
}
