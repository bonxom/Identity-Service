package com.example.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
    //    @Mapping(target = "roles", ignore = true)
    //    @Mapping(source = "id", target = "id")
    UserResponse toUserResponse(User user); // for security, we need to response by userResponse
    // -> separate Presentation (API) and Domain (Entity) layer

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
