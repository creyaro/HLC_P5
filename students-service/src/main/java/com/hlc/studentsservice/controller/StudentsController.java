package com.hlc.studentsservice.controller;

import com.hlc.studentsservice.model.SubjectsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentsController {
    @Autowired
    private SubjectsClient subjectsClient;

    @GetMapping("/subjectsForStudents")
    public String getSubjectsForStudents() {
        List<String> subjects = subjectsClient.getAllSubjects();
        return "Students can enroll at " + subjects.size() + " subjects.";
    }

}
