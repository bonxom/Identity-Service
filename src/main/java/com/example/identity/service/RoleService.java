package com.example.identity.service;

import com.example.identity.dto.request.RoleRequest;
import com.example.identity.dto.response.RoleResponse;
import com.example.identity.entity.Permission;
import com.example.identity.entity.Role;
import com.example.identity.mapper.RoleMapper;
import com.example.identity.mapper.UserMapper;
import com.example.identity.repository.PermissionRepository;
import com.example.identity.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }


    public List<RoleResponse> getAll(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String name){
        roleRepository.deleteById(name);
    }
}
