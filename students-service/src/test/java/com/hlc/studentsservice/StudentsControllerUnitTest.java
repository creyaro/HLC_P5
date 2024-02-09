package com.hlc.studentsservice;

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
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentsControllerUnitTest {
    @Mock
    private StudentsRepository studentsRepository;

    @Mock
    private SubjectsClient subjectsClient;

    @InjectMocks
    private  StudentsController studentsController;

    @Test
    void testCreateStudent_Success() {
        // Se configura el comportamiento del mock
        Student student = new Student("John", "2000-01-29", "12345678A");
        when(studentsRepository.save(any(Student.class))).thenReturn(student);

        // Se realiza la llamada al método
        ResponseEntity<String> response = studentsController.createStudent(student);

        // Se verifica la respuesta
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Verifica que la respuesta es OK
        Assertions.assertTrue(response.getBody().equals(student.toString())); // Verifica que la respuesta se corresponde con el objeto creado
        verify(studentsRepository, times(1)).save(any(Student.class)); // Verifica que se llama al método save del repositorio una vez
    }
    @Test
    void testCreateStudent_MissingRequiredFields() {
        Student student = new Student(); // Student sin campos obligatorios

        ResponseEntity<String> response = studentsController.createStudent(student);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Fields name, birthDate and dni are required.")); // Verifica que la respuesta contiene el mensaje de error esperado
        verify(studentsRepository, never()).save(any(Student.class)); // Verifica que no se llama al método save del repositorio
    }
    @Test
    void testCreateStudent_FutureBirthDate() {
        Student student = new Student("Alice", "2100-01-01", "87654321B"); // Fecha de nacimiento en el futuro

        ResponseEntity<String> response = studentsController.createStudent(student);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains("Field birth_date must be a past date.")); // Verifica que la respuesta contiene el mensaje de error esperado
        verify(studentsRepository, never()).save(any(Student.class)); // Verifica que no se llama al método save del repositorio
    }
}