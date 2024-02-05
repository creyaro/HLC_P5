package com.hlc.studentsservice.model.repository;

import com.hlc.studentsservice.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findAll();
}
