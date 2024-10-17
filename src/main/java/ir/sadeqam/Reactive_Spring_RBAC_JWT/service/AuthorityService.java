package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorityService {

    Mono<Authority> insert(Authority authority);
    Mono<Authority> find(Long id);
    Mono<Authority> findByAuthority(String authority);
}
