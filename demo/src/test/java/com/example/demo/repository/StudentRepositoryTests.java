package com.example.demo.repository;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class StudentRepositoryTests {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void givenStudent_whenSave_thenReturnSavedStudent(){
        Student student = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();

        Assertions.assertEquals(student,studentRepository.save(student));
    }

    @Test
    public void givenStudentsList_whenSaveAll_thenReturnSavedStudents(){
        Student student1 = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();
        Student student2 = Student.builder()
                .name("Adam")
                .email("adam@abc.com")
                .dob(LocalDate.of(2000, Month.MAY, 15))
                .build();

        List<Student> studentsToSave = List.of(student1,student2);

        Assertions.assertEquals(studentsToSave,studentRepository.saveAll(studentsToSave));
    }

    @Test
    public void givenIdAndStudent_whenExistsById_thenReturnTrue(){
        Student student = Student.builder()
                .name("Adam")
                .email("adam@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 5))
                .build();

        studentRepository.save(student);

        boolean isStudentAvailable = studentRepository.existsById(1L);

        Assertions.assertTrue(isStudentAvailable);
    }

    @Test
    public void givenStudent_whenFindById_thenReturnStudent(){
        Student student = Student.builder()
                .name("Adam")
                .email("adam@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 5))
                .build();

        studentRepository.save(student);

        Optional<Student> foundStudent = studentRepository.findById(1L);

        Assertions.assertTrue(foundStudent.isPresent());
    }

    @Test
    public void givenStudentsList_whenFindAll_thenReturnStudentsList(){
        Student student1 = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();
        Student student2 = Student.builder()
                .name("Adam")
                .email("adam@abc.com")
                .dob(LocalDate.of(2000, Month.MAY, 15))
                .build();

        studentRepository.saveAll(List.of(student1,student2));

        Assertions.assertEquals(2,studentRepository.findAll().size());
    }

    @Test
    public void givenStudent_whenFindStudentByEmail_thenReturnStudent(){
        final String email = "adam@abc.com";

        Student student = Student.builder()
                .name("Adam")
                .email(email)
                .dob(LocalDate.of(2000, Month.APRIL, 5))
                .build();

        studentRepository.save(student);

        Optional<Student> foundStudent = studentRepository.findStudentByEmail(email);

        Assertions.assertTrue(foundStudent.isPresent());
    }

    @Test
    public void givenStudentAndId_whenDeleteById_thenDeleteStudent(){
        Student student = Student.builder()
                .name("Adam")
                .email("adam@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 5))
                .build();

        studentRepository.save(student);

        Assertions.assertTrue(studentRepository.findById(1L).isPresent());

       studentRepository.deleteById(1L);

        Assertions.assertTrue(studentRepository.findById(1L).isEmpty());
    }
}
