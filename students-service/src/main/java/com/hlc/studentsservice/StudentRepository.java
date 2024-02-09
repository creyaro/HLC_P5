package com.hlc.studentsservice;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    //Método para buscar todas las asignaturas
    List<Student> findAll();
}
