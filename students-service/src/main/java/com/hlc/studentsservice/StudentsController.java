package com.hlc.studentsservice;

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
public class StudentsController  {

    @Autowired
    private SubjectsClient subjectsClient;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/subjectsForStudents")
    public String getSubjectsForStudents() {
        List<String> subjects = subjectsClient.getAllSubjects();
        return "Students can enroll at " + subjects.size() + "subjects.";
    }

    @PostMapping("/students")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        // Se verifica que los campos requeridos no sean nulos
        if (student.getName() == null || student.getBirth_date() == null || student.getDni() == null) {
            return ResponseEntity.badRequest().body("Fields name, birth_date and dni are required.");
        }

        // Se verifica que la fecha de nacimiento sea pasada
        LocalDate birthDate = LocalDate.parse(student.getBirth_date());
        LocalDate currentDate = LocalDate.now();
        if (birthDate.isAfter(currentDate)) {
            return ResponseEntity.badRequest().body("Field birth_date must be a past date.");
        }

        // Se crea el estudiante en bd
        Student persistedStudent = studentRepository.save(new Student(student.getName(), student.getBirth_date(), student.getDni()));

        return ResponseEntity.status(HttpStatus.CREATED).body(persistedStudent.toString());
    }

    @GetMapping("/example")
    public List<Student> getExampleStudents() {
        return Arrays.asList(
                new Student("1", "John", "29/01/2000", "12312312A"),
                new Student("2", "Alice", "12/04/2003", "87654321B"));
    }
}