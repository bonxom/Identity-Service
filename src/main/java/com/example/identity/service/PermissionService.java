package com.example.identity.service;

import com.example.identity.dto.request.PermissionRequest;
import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.PermissionResponse;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.Permission;
import com.example.identity.entity.User;
import com.example.identity.enums.Role;
import com.example.identity.exception.AppException;
import com.example.identity.exception.ErrorCode;
import com.example.identity.mapper.PermissionMapper;
import com.example.identity.mapper.UserMapper;
import com.example.identity.repository.PermissionRepository;
import com.example.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String id){
        permissionRepository.deleteById(id);
    }
}
