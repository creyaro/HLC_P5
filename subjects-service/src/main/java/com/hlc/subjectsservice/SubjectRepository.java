package com.hlc.subjectsservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    // Método para buscar todas las asignaturas
    @Override
    List<Subject> findAll();
    @Override
    void deleteById(String id);
    @Override
    Subject save(Subject subject);
    @Override
    Optional<Subject> findById(String id);

}
