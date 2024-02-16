package com.hlc.subjectsservice;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

public interface Subjects {
    @GetMapping("/subjects")
    List<Subject> getAllSubjects();
    @DeleteMapping("/subjects")
    void deleteById(String id);
    @PostMapping("/subjects")
    Subject save(Subject subject);
    @GetMapping("/subjects")
    Optional<Subject> findById(String id);

    boolean update(Subject subject);
}
