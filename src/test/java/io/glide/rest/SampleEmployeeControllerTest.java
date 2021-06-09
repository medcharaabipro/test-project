package io.glide.rest;

import io.glide.boot.api.UserApi;
import io.glide.boot.domain.Department;
import io.glide.boot.domain.User;
import io.glide.boot.repository.UserRepository;
import io.glide.boot.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

import static org.mockito.Mockito.times;

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = UserApi.class)
//@Import(UserService.class)
public class SampleEmployeeControllerTest {
    /*@MockBean
    UserRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Zayd");
        user.setLastName("Amr");
        user.setDepartment(new Department("HR"));

        Mockito.when(repository.save(user)).thenReturn(user);

        webClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(user);
    }*/

    /*@Test
    void testGetUsersByName() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Zayd");
        user.setLastName("Amr");
        user.setDepartment(new Department("HR"));

        List<User> list = new ArrayList<User>();
        list.add(user);

        Flux<User> userFlux = Flux.fromIterable(list);

        Mockito.when(repository.findByFirstName("Test")).thenReturn(userFlux);

        webClient.get().uri("/name/{name}", "Test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class);

        Mockito.verify(repository, times(1)).findByFirstName("Test");
    }*/

    /*@Test
    void testGetUserById() {
        User user = new User();
        user.setId(100L);
        user.setFirstName("Zayd");
        user.setLastName("Amr");
        user.setDepartment(new Department("HR"));

        Mockito.when(repository.findById(100L)).thenReturn(Optional.of(user));

        webClient.get().uri("/{id}", 100)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.id").isEqualTo(100)
                .jsonPath("$.name").isEqualTo("Test")
                .jsonPath("$.salary").isEqualTo(1000);

        Mockito.verify(repository, times(1)).findById(100L);
    }*/

/*    @Test
    void testDeleteUser() {
        Mono<Void> voidReturn = Mono.empty();
        Mockito.when(repository.deleteById(1L)).thenReturn(voidReturn);

        webClient.get().uri("/delete/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }*/
}