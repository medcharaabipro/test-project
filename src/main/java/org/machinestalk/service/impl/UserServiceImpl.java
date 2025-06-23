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

  private UserRepository userRepository;
  private final ModelMapper mapper;

  public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @Override
  public User registerUser(final UserRegistrationDto userRegistrationDto) {
    // implement me !!
    User user = this.mapper.map(userRegistrationDto, User.class);
    return userRepository.save(user);
  }

  @Override
  public Mono<User> getById(final long id) {
    // implement me !!
    return Mono.justOrEmpty(userRepository.findById(id));
  }
}