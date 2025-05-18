package org.machinestalk.api;

import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
public interface UserApi {

    /**
     * Register a new user.
     *
     * @param userRegistrationDto DTO that input data needed to register a new user.
     * @return registered user infos.
     */
    @PostMapping(path = "/register", consumes = APPLICATION_JSON_VALUE)
    Mono<UserDto> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto);

    /**
     * Get user details by id.
     *
     * @param userId user id
     * @return user infos
     */
    @GetMapping("/{userId}")
    Mono<UserDto> findUserById(@PathVariable Long userId);
}