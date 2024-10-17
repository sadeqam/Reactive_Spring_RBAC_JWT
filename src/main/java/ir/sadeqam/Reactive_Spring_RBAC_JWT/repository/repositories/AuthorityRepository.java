package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Mono;

public interface AuthorityRepository extends R2dbcRepository<Authority, Long> {

    Mono<Authority> findByAuthority(String authority);

}
