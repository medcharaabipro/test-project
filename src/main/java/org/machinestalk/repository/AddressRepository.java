package org.machinestalk.repository;

import org.machinestalk.domain.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AddressRepository extends ReactiveCrudRepository<Address, Long> {
    Flux<Address> findByUserId(Long userId);
}
