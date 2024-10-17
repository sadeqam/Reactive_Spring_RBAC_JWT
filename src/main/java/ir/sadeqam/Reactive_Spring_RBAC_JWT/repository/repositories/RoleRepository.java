package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Role;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends R2dbcRepository<Role, Long> {

    Mono<Role> findByName(String name);

}
