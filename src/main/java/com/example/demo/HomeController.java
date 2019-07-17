package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    TODORepository todoRepository;

    @RequestMapping("/")
    public String listTodo(Model model){
        model.addAttribute("Todo", todoRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String todoForm(Model model){
        model.addAttribute("todo", new TODO());
        return "todoform";
    }

    @PostMapping("/process")
    public String processForm(@Valid TODO todo,
                              Model model,
                              BindingResult result){
        if (result.hasErrors()){
            model.addAttribute("todo", todo);
            return "todoform";
        }
        model.addAttribute("todo", todo);
        todoRepository.save(todo);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showtodo(@PathVariable("id") long id, Model model){
        model.addAttribute("todo", todoRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateTODO(@PathVariable("id")long id, Model model){
        model.addAttribute("todo", todoRepository.findById(id).get());
        return "todoform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id")long id){
        todoRepository.deleteById(id);
        return "redirect:/";
    }

}
