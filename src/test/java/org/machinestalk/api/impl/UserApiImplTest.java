package org.machinestalk.api.impl;

import org.junit.jupiter.api.Test;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;



import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest(UserApiImpl.class)
class UserApiImplTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private UserService userService;

  @MockBean
  private ModelMapper modelMapper;

  @Test
  void testRegisterUser_shouldReturnUserDto() {
    UserRegistrationDto registrationDto = new UserRegistrationDto();
    registrationDto.setFirstName("John");
    registrationDto.setLastName("Doe");
    registrationDto.setDepartment("IT");

    AddressDto addressDto = new AddressDto();
    addressDto.setStreetNumber("1");
    addressDto.setStreetName("Main Street");
    addressDto.setPostalCode("75001");
    addressDto.setCity("Paris");
    addressDto.setCountry("France");
    registrationDto.setPrincipalAddress(addressDto);

    // Add valid secondaryAddress to avoid validation errors
    AddressDto secondaryAddressDto = new AddressDto();
    secondaryAddressDto.setStreetNumber("2");
    secondaryAddressDto.setStreetName("Second Street");
    secondaryAddressDto.setPostalCode("75002");
    secondaryAddressDto.setCity("Paris");
    secondaryAddressDto.setCountry("France");
    registrationDto.setSecondaryAddress(secondaryAddressDto);

    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setFirstName("John");
    mockUser.setLastName("Doe");

    UserDto expectedDto = new UserDto("1",
            new UserDto.UserInfos("John", "Doe", "IT",
                    Collections.singletonList("1 Main Street, 75001 Paris, France")));

    when(userService.registerUser(any())).thenReturn(mockUser);
    when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(expectedDto);

    webTestClient.post()
            .uri("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registrationDto)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo("1")
            .jsonPath("$.userInfos.firstName").isEqualTo("John")
            .jsonPath("$.userInfos.department").isEqualTo("IT");
  }

  @Test
  void testFindUserById_shouldReturnUserDto() {
    long userId = 1L;

    User mockUser = new User();
    mockUser.setId(userId);
    mockUser.setFirstName("Alice");
    mockUser.setLastName("Smith");

    Department department = new Department();
    department.setName("HR");
    mockUser.setDepartment(department);

    Address address = new Address();
    address.setStreetNumber("10");
    address.setStreetName("Rue Voltaire");
    address.setPostalCode("75011");
    address.setCity("Paris");
    address.setCountry("France");

    mockUser.setAddresses(new HashSet<>(Collections.singletonList(address)));

    UserDto expectedDto = new UserDto("1",
            new UserDto.UserInfos("Alice", "Smith", "HR",
                    Collections.singletonList("10 Rue Voltaire, 75011 Paris, France")));

    when(userService.getById(userId)).thenReturn(Mono.just(mockUser));
    when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(expectedDto);

    webTestClient.get()
            .uri("/users/{userId}", userId)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo("1")
            .jsonPath("$.userInfos.firstName").isEqualTo("Alice")
            .jsonPath("$.userInfos.department").isEqualTo("HR")
            .jsonPath("$.userInfos.adresses[0]").isEqualTo("10 Rue Voltaire, 75011 Paris, France");
  }
}