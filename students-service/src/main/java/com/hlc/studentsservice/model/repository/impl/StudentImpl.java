package com.hlc.studentsservice.model.repository.impl;
import com.hlc.studentsservice.model.Students;
import com.hlc.studentsservice.model.Student;
import com.hlc.studentsservice.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class StudentImpl implements Students {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/subjects")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
