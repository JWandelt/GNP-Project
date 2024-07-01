package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void givenStudent_whenAddNewStudent_returnStudent(){
        Student student = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();

        when(studentRepository.findStudentByEmail("tom123@abc.com")).thenReturn(Optional.empty());

        studentService.addNewStudent(student);

        verify(studentRepository,times(1)).save(student);

    }

    @Test
    public void givenId_whenDeleteStudent_deleteStudent(){
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository,times(1)).deleteById(1L);

    }

    @Test
    public void givenIdAndName_whenUpdateStudent_updateStudentData(){
        Student student = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.updateStudent(1L,"Jack","jack444@ghj.com");

        Assertions.assertEquals("Jack",student.getName());
        Assertions.assertEquals("jack444@ghj.com",student.getEmail());
    }

}
