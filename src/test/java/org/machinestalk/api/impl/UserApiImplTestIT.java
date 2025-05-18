package org.machinestalk.api.impl;

import org.junit.jupiter.api.Test;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(UserApiImpl.class)
class UserApiImplTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;


    @Test
    void When_RegisterApiCalled_Expect_UserIsRegistered() {
        // Arrange
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setFirstName("Jamil");
        registrationDto.setLastName("Tester");
        registrationDto.setDepartment("North");
        AddressDto principalAddressDto = new AddressDto();
        principalAddressDto.setCity("San Francisco");
        principalAddressDto.setCountry("US");
        principalAddressDto.setPostalCode("9410");
        principalAddressDto.setStreetName("Test Street");
        principalAddressDto.setStreetNumber("12");
        registrationDto.setPrincipalAddress(principalAddressDto);
        Long userId = 123L;
        User mockUser = new User();
        mockUser.setFirstName("Jamil");
        mockUser.setLastName("Tester");
        mockUser.setId(userId);
        mockUser.setDepartmentId(125L);
        Mockito.when(userService.getById(any(Long.class))).thenReturn(Mono.<User>just(mockUser));
        Mockito.when(userService.registerUser(Mockito.any(UserRegistrationDto.class)))
                .thenReturn(Mono.just(mockUser));

        // Act & Assert
        webTestClient.post()
                .uri("/users/register")
                .bodyValue(registrationDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(user -> {
                    assertNotNull(user);
                    assertEquals("Jamil", user.getUserInfos().getFirstName());
                    assertEquals("Tester", user.getUserInfos().getLastName());
                });
    }

    @Test
    void When_FindUserByIdApiCalled_Expect_UserIsFound() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setId(userId);
        Mockito.when(userService.getById(any(Long.class))).thenReturn(Mono.<User>just(mockUser));

        // Act & Assert
        webTestClient.get()
                .uri("/users/{id}", userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(user -> {
                    assertNotNull(user);
                    assertEquals("John", user.getUserInfos().getFirstName());
                    assertEquals("Doe", user.getUserInfos().getLastName());
                });
    }
}
