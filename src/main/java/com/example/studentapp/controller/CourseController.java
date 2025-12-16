package com.example.studentapp.controller;


import com.example.studentapp.entity.Course;
import com.example.studentapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", repo.findAll());
        model.addAttribute("title", "Courses");
        model.addAttribute("content", "courses/courseList");
        return "layout";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("title", "Add Course");
        model.addAttribute("content", "courses/addCourse");
        return "layout";
    }

    @PostMapping("/save")
    public String save(Course c) {
        repo.save(c);
        return "redirect:/courses";
    }
}

