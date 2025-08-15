package com.example.identity.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import com.example.identity.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    List<String> roles;
}
