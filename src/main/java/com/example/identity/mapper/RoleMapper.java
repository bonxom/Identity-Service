package com.example.identity.mapper;

import com.example.identity.dto.request.RoleRequest;
import com.example.identity.dto.response.RoleResponse;
import com.example.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper{
    @Mapping(target = "permissions", ignore = true)
    Role toRole (RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
