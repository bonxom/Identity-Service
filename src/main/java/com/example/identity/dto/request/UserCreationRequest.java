package com.example.identity.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import com.example.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "DOB_INVALID")
    LocalDate dob;

    List<String> roles;
}
