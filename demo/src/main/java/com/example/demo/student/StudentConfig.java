package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(1L, "Mariam", LocalDate.of(2000, Month.APRIL, 5), "something@gmail.com");
            Student alex = new Student("Mariam", LocalDate.of(2000, Month.APRIL, 5), "ssomething@gmail.com");

            studentRepository.saveAll(List.of(mariam, alex));
        };
    };
}
