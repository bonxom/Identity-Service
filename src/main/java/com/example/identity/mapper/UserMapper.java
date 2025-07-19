package com.example.identity.mapper;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    @Mapping(source = "id", target = "id")
    UserResponse toUserResponse(User user); //for security, we need to response by userResponse
                                            // -> separate Presentation (API) and Domain (Entity) layer
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
