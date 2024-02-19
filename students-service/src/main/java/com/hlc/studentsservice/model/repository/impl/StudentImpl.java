package com.hlc.studentsservice.model.repository.impl;
import com.hlc.studentsservice.model.Students;
import com.hlc.studentsservice.model.Student;
import com.hlc.studentsservice.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController

public class StudentImpl implements Students {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @GetMapping("/example")
    public List<Student> getExampleStudents() {
        return Arrays.asList(
                new Student("John", "29/01/2000", "12312312A"),
                new Student("Alice", "12/04/2003", "87654321B"));
    }

    @PostMapping("/student")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        // Se verifica que los campos requeridos no sean nulos
        if (student.getName() == null || student.getBirthDate() == null || student.getDni() == null) {
            return ResponseEntity.badRequest().body("Fields name, birth_date and dni are required.");
        }

        // Se verifica que la fecha de nacimiento sea pasada
        LocalDate birthDate = LocalDate.parse(student.getBirthDate());
        LocalDate currentDate = LocalDate.now();
        if (birthDate.isAfter(currentDate)) {
            return ResponseEntity.badRequest().body("Field birthDate must be a past date.");
        }

        // Se crea el estudiante en bd
        Student persistedStudent = studentRepository.save(new Student(student.getName(), student.getBirthDate(), student.getDni()));

        return ResponseEntity.status(HttpStatus.CREATED).body(persistedStudent.toString());
    }

}
