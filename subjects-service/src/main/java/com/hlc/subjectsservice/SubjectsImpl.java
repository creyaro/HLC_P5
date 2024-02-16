package com.hlc.subjectsservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
public class SubjectsImpl implements Subjects {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    @DeleteMapping("/subjects")
    public void deleteById(String id) {

    }

    @Override
    @PostMapping("/subjects")
    public Subject save(Subject subject) {
        return null;
    }

    @Override
    @GetMapping("/subject")
    public Optional<Subject> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Subject subject) {
        boolean editado = true;
        try {
            deleteById(subject.getId());
            save(subject);
        }catch (Exception e){
            editado=false;
        }
        return editado;
    }
}
