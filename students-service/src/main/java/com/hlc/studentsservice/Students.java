package com.hlc.studentsservice;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

public interface Students {
    @GetMapping("/subjects")
    List<Student> getAllSubjects();
    @DeleteMapping("/subjects")
    void deleteById(String id);
    @PostMapping("/subjects")
    Student save(Student subject);
    @GetMapping("/subjects")
    Optional<Student> findById(String id);

    boolean update(Student subject);
}
