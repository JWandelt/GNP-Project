package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getProductByName_whenIsNotFound() throws Exception{
        when(productService.getProductByName("smartphone")).thenThrow(new ResourceNotFoundException("Product not found"));
        mockMvc.perform(get("/api/product/smartphone"))
                .andExpect(status().isNotFound());
    }

   /* @Test
    public void whenGetStudents_thenReturnListOFStudents() throws Exception{
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

        when(productService.getStudents()).thenReturn(List.of(student1,student2));

        mockMvc.perform(get("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[1].name").value("Adam"));

        verify(productService,times(1)).getStudents();
    }

    @Test
    public void whenRegisterNewStudent_thenAddNewStudent() throws Exception{
        Student student = Student.builder()
                .name("Tom")
                .email("tom123@abc.com")
                .dob(LocalDate.of(2000, Month.APRIL, 10))
                .build();

        mockMvc.perform(post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());

        verify(productService,times(1)).addNewStudent(student);
    }

    @Test
    public void whenDeleteStudent_thenRemoveStudent() throws Exception{
        mockMvc.perform(delete("/api/v1/student/1"))
                .andExpect(status().isOk());

        verify(productService,times(1)).deleteStudent(1L);
    }

    @Test
    public void whenUpdateStudent_thenChangeStudentData() throws Exception{
        mockMvc.perform(put("/api/v1/student/1")
                        .param("name","Leo")
                        .param("email","leo265@abc.com"))
                .andExpect(status().isOk());

        verify(productService,times(1)).updateStudent(1L,"Leo","leo265@abc.com");
    }*/
}
