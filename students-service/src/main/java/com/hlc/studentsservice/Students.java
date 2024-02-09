package com.hlc.studentsservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface Students {
    @GetMapping("/students")
    List<Student> getAllStudents();

    @PostMapping("/saveStudents")
    void saveStudents(Student student);
}
