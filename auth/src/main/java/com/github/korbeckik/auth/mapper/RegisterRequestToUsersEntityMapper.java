package com.github.korbeckik.auth.mapper;

import com.github.korbeckik.auth.dto.request.RegisterRequest;
import com.github.korbeckik.common.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RegisterRequestToUsersEntityMapper.class})
public interface RegisterRequestToUsersEntityMapper {
    RegisterRequestToUsersEntityMapper INSTANCE = Mappers.getMapper(RegisterRequestToUsersEntityMapper.class);

    @Mapping(ignore = true, target = "roles")
    @Mapping(ignore = true, target = "id")
    UsersEntity sourceToDestination(RegisterRequest source);
    RegisterRequest destinationToSource(UsersEntity destination);
}
