package com.example.identity.controller;

import com.example.identity.dto.request.ApiResponse;
import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.exception.AppException;
import com.example.identity.exception.ErrorCode;
import com.example.identity.repository.UserRepository;
import com.example.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
//        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(userService.CreateUser(request));
//        return apiResponse;
        return ApiResponse.<UserResponse>builder()
                .result(userService.CreateUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {

        //log user role (scope)
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(
                grantedAuthority -> log.info(grantedAuthority.getAuthority())
        );


        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
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
    ApiResponse<UserResponse> updateUser(@PathVariable("userID") String userID, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.UpdateUser(userID, request))
                .build();
    }

    @DeleteMapping("/{userID}")
    ApiResponse<UserResponse> deleteUser(@PathVariable("userID") String userID){
        UserResponse userResponse = userService.deleteUser(userID);
        if (userResponse != null) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return ApiResponse.<UserResponse>builder()
                .message("User has been deleted")
                .build();
    }
}
