package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findByUsername(String username);


}
