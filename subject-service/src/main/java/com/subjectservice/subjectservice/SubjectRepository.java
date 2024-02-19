package com.subjectservice.subjectservice;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    // Método para buscar todas las asignaturas
    List<Subject> findAll();
}