package com.hlc.studentsservice.model;

import com.hlc.studentsservice.model.Student;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface Students {
    @GetMapping("/students")
    List<Student> getAllStudents();
}
