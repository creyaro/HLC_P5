package com.hlc.studentsservice;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String dni;
    private String birthDate;

    public Student(String name, String birthDate,String dni) {
        this.name = name;
        this.dni = dni;
        this.birthDate = birthDate;
    }

    public Student() {

    }

    public Student(String id, String name, String dni, String birthDate) {
        this.id=id;
        this.name = name;
        this.dni = dni;
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
