package com.example.studentapp.repository;



import com.example.studentapp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findByNameContaining(String keyword, Pageable pageable);
}
