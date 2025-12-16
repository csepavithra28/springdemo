package com.example.studentapp.controller;



import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping({"/", "/user/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("studentCount", studentRepo.count());
        model.addAttribute("courseCount", courseRepo.count());
        model.addAttribute("title", "Dashboard");
        model.addAttribute("content", "user/dashboard");
        return "layout";
    }
    @GetMapping({"/", "/admin/dashboard"})
    public String dashboard1(Model model) {
        model.addAttribute("studentCount", studentRepo.count());
        model.addAttribute("courseCount", courseRepo.count());
        model.addAttribute("title", "Dashboard");
        model.addAttribute("content", "admin/dashboard");
        return "layout";
    }
}

