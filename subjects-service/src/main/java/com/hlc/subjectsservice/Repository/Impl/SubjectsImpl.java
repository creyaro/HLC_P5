package com.hlc.subjectsservice.Repository.Impl;

import com.hlc.subjectsservice.Repository.SubjectRepository;
import com.hlc.subjectsservice.Subject;
import com.hlc.subjectsservice.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SubjectsImpl implements Subjects {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    @GetMapping("/subjects/{id}")
    public Optional<Subject> getStudentById(@PathVariable String id){
        return subjectRepository.findById(id);
    }

    @PostMapping("/subjects")
    public Subject save(@RequestBody Subject student){
        return subjectRepository.save(student);
    }

    @PutMapping("/subjects/{id}")
    public Subject updateStudent(@RequestBody Subject student){
        return subjectRepository.save(student);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity deleteStudentById(@PathVariable String id){
        try {
            subjectRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects")
    public ResponseEntity deleteAllStudent(){
        try {
            subjectRepository.deleteAll();
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
