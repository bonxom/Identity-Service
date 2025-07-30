package com.example.identity.controller;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc //create mock test to controller
//mock: đối tượng giả lập (giả mạo) được tạo ra để thay thế các phần phụ thuộc thực tế,
// giúp kiểm thử đơn vị mã (unit) một cách cô lập.
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; //call to our API

    //@MockBean
    @MockitoBean //mock bean user service //version 3.5 use @MockitoBean instead of using @MockBean
    UserService userService;

    private UserResponse response;
    private UserCreationRequest request;
    private LocalDate dob;

    @BeforeEach
    void initData(){
        dob = LocalDate.of(2005, 9, 7);
        request = UserCreationRequest.builder()
                .username("bonxom")
                .password("12345678")
                .firstName("Ho")
                .lastName("Dung")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("abcxyz")
                .username("bonxom")
                .firstName("Ho")
                .lastName("Dung")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {

        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

            //to mock method in user service
        when(userService.createUser(any())).thenReturn(response);

        //WHEN //THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.id").value("abcxyz"));
        //THEN
    }

    @Test
    void createUser_invalidUsername_fail() throws Exception {

        //GIVEN
        request.setUsername("bonxo");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        //to mock method in user service
//        when(userService.createUser(any())).thenReturn(response);
        //because validation call at controller, thus userService has not been called

        //WHEN //THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(2001))
                .andExpect(jsonPath("message").value("Username must be at least 6 characters"));
        //THEN
    }

    @Test
    void createUser_invalidPassword_fail() throws Exception {

        //GIVEN
        request.setPassword("abcxyz");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        //to mock method in user service
//        when(userService.createUser(any())).thenReturn(response);
        //because validation call at controller, thus userService has not been called

        //WHEN //THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(2002))
                .andExpect(jsonPath("message").value("Password must be at least 8 characters"));
        //THEN
    }

    @Test
    void createUser_invalidDob_fail() throws Exception {

        //GIVEN
        request.setDob(LocalDate.of(2020, 9, 7));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        //to mock method in user service
//        when(userService.createUser(any())).thenReturn(response);
        //because validation call at controller, thus userService has not been called

        //WHEN //THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(2003))
                .andExpect(jsonPath("message").value("User must be at least 16 years old"));
        //THEN
    }
}
