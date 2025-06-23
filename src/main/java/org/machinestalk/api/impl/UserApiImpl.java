package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
public class UserApiImpl implements UserApi {

    private final UserService userService;
    private final ModelMapper mapper;

    public UserApiImpl(final UserService userService, final ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public UserDto register(final UserRegistrationDto userRegistrationDto) {
        // implement me !!
        User user = userService.registerUser(userRegistrationDto);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public Mono<UserDto> findUserById(long id) {
        // implement me !!
        return userService.getById(id)
                .map(user -> {
                    UserDto.UserInfos infos = new UserDto.UserInfos(
                            user.getFirstName(),
                            user.getLastName(),
                            user.getDepartment() != null ? user.getDepartment().getName() : null,
                            user.getAddresses() != null
                                    ? user.getAddresses().stream()
                                    .map(a -> String.format("%s %s, %s %s, %s",
                                            a.getStreetNumber(), a.getStreetName(),
                                            a.getPostalCode(), a.getCity(),
                                            a.getCountry()))
                                    .collect(Collectors.toList())
                                    : Collections.emptyList()
                    );
                    return new UserDto(String.valueOf(user.getId()), infos);
                });
    }
}
