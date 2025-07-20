package com.example.identity.mapper;

import com.example.identity.dto.request.PermissionRequest;
import com.example.identity.dto.response.PermissionResponse;
import com.example.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
//    void updatePermission(@MappingTarget Permission permission, UserUpdateRequest request);
}
