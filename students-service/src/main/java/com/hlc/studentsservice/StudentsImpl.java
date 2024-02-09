package com.hlc.studentsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentsImpl implements Students{

    @Autowired
    private StudentsRepository studentsRepository;
    @Override
    @GetMapping("/students")
    public List<Student> getAllSubjects() {
        return studentsRepository.findAll();
    }

    @Override
    @DeleteMapping("/student/{id}")
    public void deleteById(@PathVariable String id) {
        studentsRepository.deleteById(id);
    }

    @Override
    @PostMapping("/student")
    public Student save(@RequestBody Student student) {
        return studentsRepository.save(student);
    }

    @Override
    @GetMapping("/student/{id}")
    public Optional<Student> findById(@PathVariable String id) {
        return studentsRepository.findById(id);
    }

    @Override
    @PutMapping("/student")
    public boolean update(@RequestBody Student student) {
        boolean editado = true;
        try {
            deleteById(student.getId());
            save(student);
        }catch (Exception e){
            editado=false;
        }
        return editado;
    }
}
