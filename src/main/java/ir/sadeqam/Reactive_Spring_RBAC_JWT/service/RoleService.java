package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {

    Mono<Role> find(Long id);
    Flux<Authority> findAllAuthorities(Long roleId);
    Mono<Role> insert(Role role);
}
