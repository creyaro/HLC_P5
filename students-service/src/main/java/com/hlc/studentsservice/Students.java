package com.hlc.studentsservice;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface Students {
    @GetMapping("/students")
    List<Student> getAllStudents();
}
