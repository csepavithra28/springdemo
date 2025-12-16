package com.example.studentapp.controller;


import com.example.studentapp.entity.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.io.File;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @Autowired
    private CourseRepository courseRepo;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }



    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        Page<Student> data = repo.findByNameContaining(keyword, PageRequest.of(page, 5));
        model.addAttribute("students", data.getContent());
        model.addAttribute("totalPages", data.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        model.addAttribute("title", "Students");
        model.addAttribute("content", "index");
        return "layout";
    }
    @GetMapping(value = "/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(@PathVariable Integer id) {
        Student s = repo.findById(id).orElse(null);
        if (s != null && s.getPhoto() != null) {
            return s.getPhoto();
        }
        return null;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("title", "Add Student");
        model.addAttribute("content", "students/addStudent");
        return "layout";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student,
                       @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            if (file != null && !file.isEmpty()) {
                // Store image in database
                student.setPhoto(file.getBytes());

                // Store image in local folder
                String uploadsDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

                File dir = new File(uploadsDir);
                if (!dir.exists()) {
                    dir.mkdirs();  // Create folder if not exists
                }

                // Remove special characters from file name
                String fileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

                File uploadFile = new File(dir, fileName);
                file.transferTo(uploadFile);
            } else {
                // If editing and no new file uploaded → keep old photo
                if (student.getId() != null) {
                    Student old = repo.findById(student.getId()).orElse(null);
                    if (old != null) {
                        student.setPhoto(old.getPhoto());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        repo.save(student);
        return "redirect:/students";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("student", repo.findById(id).orElse(null));
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("title", "Edit Student");
        model.addAttribute("content", "students/editStudent");
        return "layout";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/students";
    }
}
