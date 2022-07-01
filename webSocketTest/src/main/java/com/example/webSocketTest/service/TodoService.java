package com.example.webSocketTest.service;

import com.example.webSocketTest.entity.Todo;
import com.example.webSocketTest.model.MessageModel;
import com.example.webSocketTest.repository.TodoRepository;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public boolean add(MessageModel model) {
        Todo todo = new Todo();
        todo.setText(model.getContent());
        try {
            todoRepository.save(todo);
        } catch (TransactionException e) {
            return false;
        }
        return true;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
