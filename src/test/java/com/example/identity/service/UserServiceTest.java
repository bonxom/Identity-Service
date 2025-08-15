package com.example.identity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.entity.User;
import com.example.identity.exception.AppException;
import com.example.identity.repository.RoleRepository;
import com.example.identity.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource("/test.properties")
@AutoConfigureMockMvc(addFilters = false) // disable security filter
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
    void initData() {
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
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        // WHEN
        var response = userService.createUser(request);
        // THEN

        assertThat(response.getId()).isEqualTo("abcxyz");
        assertThat(response.getUsername()).isEqualTo("bonxom");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));
        // THEN
        assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("User existed");
    }

    @Test
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"})
    void deleteUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsById(user.getId())).thenReturn(true);

        // WHEN
        userService.deleteUser(user.getId());

        // THEN
        verify(userRepository).deleteById(user.getId()); // ensure delete function is called
    }

    @Test
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"})
    void deleteUser_nonexistentUser_fail() {
        // GIVEN
        when(userRepository.existsById(user.getId())).thenReturn(false);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.deleteUser(user.getId()));

        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
        assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @WithMockUser(username = "bonxom")
    void getMyInfo_validRequest_success() {
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // WHEN
        var response = userService.getMyInfo();
        // THEN
        assertThat(response.getUsername()).isEqualTo("bonxom");
        assertThat(response.getId()).isEqualTo("abcxyz");
    }

    @Test
    @WithMockUser(username = "bonxom")
    void getMyInfo_unAuthenticated_fail() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        assertThat(exception.getErrorCode().getCode()).isEqualTo(3001);
        assertThat(exception.getErrorCode().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
