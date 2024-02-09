package com.hlc.studentsservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="students")
public class Student {

    @Id
    private String id;
    private String name;
    private String birth_date;
    private String dni;

    public Student() {
    }

    public Student(String name, String birth_date, String dni) {
        this.name = name;
        this.birth_date = birth_date;
        this.dni = dni;
    }

    public Student(String id, String name, String birth_date, String dni) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.dni = dni;
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

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
