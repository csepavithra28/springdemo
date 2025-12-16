package com.example.studentapp.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private Integer age;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Student() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
