package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.RoleAuthority;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface RoleAuthorityRepository extends R2dbcRepository<RoleAuthority, Long> {

    Flux<RoleAuthority> findByRoleId(Long id);

}
