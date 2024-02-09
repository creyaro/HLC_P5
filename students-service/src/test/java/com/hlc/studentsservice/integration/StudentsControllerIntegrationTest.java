package com.hlc.studentsservice.integration;

import com.hlc.studentsservice.Student;
import com.hlc.studentsservice.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void testCreateStudent() throws Exception {
        // Se configura el comportamiento del mock del repositorio
        Student student = new Student("John", "2000-01-29", "12345678A");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Se realiza la solicitud POST al endpoint /students
        String studentJson = "{\"name\": \"John\", \"birth_date\": \"2000-01-29\", \"dni\": \"12345678A\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated());
    }
}
