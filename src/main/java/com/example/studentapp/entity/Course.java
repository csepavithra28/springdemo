package com.example.studentapp.entity;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseName;

    public Course() {}
    public Course(String courseName) { this.courseName = courseName; }

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
}
