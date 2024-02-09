package com.hlc.studentsservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends MongoRepository<Student, String> {

    @Override
    List<Student> findAll();
    @Override
    void deleteById(String id);
    @Override
    Student save(Student subject);
    @Override
    Optional<Student> findById(String id);
}
