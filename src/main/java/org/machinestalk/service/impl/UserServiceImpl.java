package org.machinestalk.service.impl;

import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<User> registerUser(final UserRegistrationDto userRegistrationDto) {
        User user = modelMapper.map(userRegistrationDto, User.class);
        if (!verifyUserDataRequirements(user)) {
            return Mono.empty();
        }
        return userRepository.save(user);
    }

    @Override
    public Mono<User> getById(final long id) {
        return this.userRepository.findById(id);
    }

    boolean verifyUserDataRequirements(final User user) {
        return user.getFirstName() != null
                && user.getLastName() != null;
    }
}