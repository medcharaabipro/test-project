package org.machinestalk.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.machinestalk.R2dbcTestConfig;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
@Import(R2dbcTestConfig.class)
class UserRepositoryTest {
    Address address;
    User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        // Given
        address = new Address();
        address.setStreetNumber("22");
        address.setStreetName("Rue Voltaire");
        address.setPostalCode("75012");
        address.setCity("Paris");
        address.setCountry("France");

        user = new User();
        user.setId(1L);
        user.setFirstName("Jack");
        user.setLastName("Sparrow");
    }

    @Test
    void Should_PersistNewUser_When_Save() {
        // when
        user.setId(null);
        StepVerifier.create(userRepository.save(user))
                .assertNext(result -> {
                    assertNotNull(result);
                    user.setId(result.getId());  // set ID for equality check if needed
                    assertEquals(user, result);
                })
                .verifyComplete();
    }

    @Test
    void Should_GetUserByItsId_When_FindById() {
        // Given
        user.setId(null);
        final Mono<User> savedUser = userRepository.save(user);

        // When
        savedUser
                .flatMap(su -> userRepository.findById(su.getId()))
                .doOnNext(res -> assertEquals(user, res))
                .switchIfEmpty(Mono.fromRunnable(Assertions::fail))
                .block(); // Only in tests – avoid in production
    }

    @Test
    void Should_returnEmptyMono_When_FindById_ForUserThatNotExistsByTheGivenId() {
        StepVerifier.create(userRepository.findById(5678L))
                .expectNextCount(0)
                .verifyComplete();
    }
}
