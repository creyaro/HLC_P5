package com.hlc.subjectsservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    // Método para buscar todas las asignaturas
    List<Subject> findAll();
}
