package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.mapper.UserMapper;
import org.machinestalk.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserApiImpl implements UserApi {

    private final UserService userService;

    public UserApiImpl(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDto> register(final UserRegistrationDto userRegistrationDto) {
        return userService
                .registerUser(userRegistrationDto)
                .map(UserMapper::fromUserToUserDto);
    }

    @Override
    public Mono<UserDto> findUserById(Long userId) {
        return userService.getById(userId).map(UserMapper::fromUserToUserDto);
    }
}
