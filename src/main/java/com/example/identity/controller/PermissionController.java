package com.example.identity.controller;

import com.example.identity.dto.request.ApiResponse;
import com.example.identity.dto.request.PermissionRequest;
import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.PermissionResponse;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.service.PermissionService;
import com.example.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name){
        permissionService.delete(name);
        return ApiResponse.<Void>builder()
                .message("Permission has been deleted")
                .build();
    }
}
