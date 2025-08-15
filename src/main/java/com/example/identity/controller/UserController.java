package com.example.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.identity.dto.request.ApiResponse;
import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: Create User");
        //        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        //        apiResponse.setResult(userService.CreateUser(request));
        //        return apiResponse;
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {

        // log user role (scope)
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping("/{userID}")
    ApiResponse<UserResponse> getUser(@PathVariable("userID") String userID) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userID))
                .build();
    }

    @PutMapping("/{userID}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable("userID") String userID, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userID, request))
                .build();
    }

    @DeleteMapping("/{userID}")
    ApiResponse<Void> deleteUser(@PathVariable("userID") String userID) {
        userService.deleteUser(userID);
        return ApiResponse.<Void>builder().message("User has been deleted").build();
    }
}
