package com.hlc.studentsservice.Repository.Impl;

import com.hlc.studentsservice.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    //Método para buscar todos los estudiantes
    List<Student> findAll();
}
