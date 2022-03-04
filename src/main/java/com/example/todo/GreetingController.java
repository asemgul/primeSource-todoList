package com.example.todo;

import com.example.todo.domain.Todo;
import com.example.todo.repos.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class GreetingController {
    @Autowired
    private TodoRepo todoRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Todo> todos = todoRepo.findAll();
        model.put("todos", todos);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam(required = true) String name, @RequestParam(required = true) String priority, @RequestParam(required = true) String description, Map<String, Object> model) {
        Todo todo = new Todo(name, priority, description);
        todoRepo.save(todo);
        Iterable<Todo> todos = todoRepo.findAll();
        model.put("todos", todos);
        return "main";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Map<String, Object> model) {
        Optional<Todo> todo = todoRepo.findById(id);
        if (todo.isPresent()){
            model.put("todo", todo.get());
        }
        return "edit";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id, Map<String, Object> model) {
        todoRepo.deleteById(id);
        Iterable<Todo> todos = todoRepo.findAll();

        model.put("todos", todos);
        return "main";
    }

    @RequestMapping("/update/{id}")
    public String update(@PathVariable(name = "id") Integer id, @RequestParam String name, @RequestParam String priority, @RequestParam String description, Map<String, Object> model) {
        Optional<Todo> todo = todoRepo.findById(id);
        todo.get().setName(name);
        todo.get().setPriority(priority);
        todo.get().setDescription(description);
        todo.get().setDate(new Date());
        todoRepo.save(todo.get());

        Iterable<Todo> todos = todoRepo.findAll();
        model.put("todos", todos);
        return "main";
    }


}