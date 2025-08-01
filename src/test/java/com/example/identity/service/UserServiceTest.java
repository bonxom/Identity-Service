package com.example.identity.service;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.exception.AppException;
import com.example.identity.repository.RoleRepository;
import com.example.identity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    private UserCreationRequest request;
    private User user;
    private LocalDate dob;

    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

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

        user = User.builder()
                .id("abcxyz")
                .username("bonxom")
                .firstName("Ho")
                .lastName("Dung")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //WHEN
        var response = userService.createUser(request);
        //THEN

        assertThat(response.getId()).isEqualTo("abcxyz");
        assertThat(response.getUsername()).isEqualTo("bonxom");
    }

    @Test
    void createUser_userExisted_fail(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        //WHEN
        var exception = assertThrows(AppException.class, ()
                -> userService.createUser(request));
        //THEN
        assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("User existed");
    }
}
