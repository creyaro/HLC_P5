package com.hlc.studentsservice.unit;

import com.hlc.studentsservice.controller.StudentsController;
import com.hlc.studentsservice.model.Student;
import com.hlc.studentsservice.model.SubjectsClient;
import com.hlc.studentsservice.model.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentsControllerUnitTest {
    // Mock del repositorio
    @Mock
    private StudentRepository studentRepository;

    // Mock de subjects-service
    @Mock
    private SubjectsClient subjectsClient;

    // Inyectamos los mock anteriores en la clase StudentsController
    @InjectMocks
    private StudentsController studentsController;

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
    @Test
    void testCreateStudent_Success() {
        // Se configura el comportamiento del mock
        Student student = new Student("John", "2000-01-29", "12345678A");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Se realiza la llamada al método
        ResponseEntity<String> response = studentsController.createStudent(student);

        // Se verifica la respuesta
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Verifica que la respuesta es OK
        Assertions.assertTrue(response.getBody().equals(student.toString())); // Verifica que la respuesta se corresponde con el objeto creado
        verify(studentRepository, times(1)).save(any(Student.class)); // Verifica que se llama al método save del repositorio una vez
    }

    @Test
    void testCreateStudent_MissingRequiredFields() {
        Student student = new Student(); // Student sin campos obligatorios

        ResponseEntity<String> response = studentsController.createStudent(student);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Fields name, birth_date and dni are required.")); // Verifica que la respuesta contiene el mensaje de error esperado
        verify(studentRepository, never()).save(any(Student.class)); // Verifica que no se llama al método save del repositorio
    }

    @Test
    void testCreateStudent_FutureBirthDate() {
        Student student = new Student("Alice", "2100-01-01", "87654321B"); // Fecha de nacimiento en el futuro

        ResponseEntity<String> response = studentsController.createStudent(student);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Field birth_date must be a past date.")); // Verifica que la respuesta contiene el mensaje de error esperado
        verify(studentRepository, never()).save(any(Student.class)); // Verifica que no se llama al método save del repositorio
    }


}
