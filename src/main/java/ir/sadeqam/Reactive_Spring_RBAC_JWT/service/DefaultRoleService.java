package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Role;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.RoleAuthority;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.RoleAuthorityRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.RoleRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class DefaultRoleService implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public Mono<Role> find(Long id) {
        return Mono.zip(
                repository.findById(id)
                        .switchIfEmpty(Mono.error(
                                new DataNotFoundException("role with id %d not found".formatted(id))
                        )),
                findAllAuthorities(id).collectList(),
                (r, authorities) -> new Role(r.getId(), r.getName(), authorities)
        );
    }

    @Override
    @Cacheable //check if needed use .cache operator in following Flux
    public Flux<Authority> findAllAuthorities(Long roleId) {
        return roleAuthorityRepository.findByRoleId(roleId)
                .switchIfEmpty(Mono.error(
                        new DataNotFoundException("no authority found for this role (role id is %d)".formatted(roleId))
                ))
                .flatMap(roleAuthority -> authorityService.find(roleAuthority.getAuthorityId()));
    }

    @Override
    @Transactional
    public Mono<Role> insert(Role role) {
        return repository.save(role)
                .delayUntil(savedRole -> roleAuthorityRepository.saveAll(
                        role.getAuthorities()
                                .stream().map(
                                        authority -> new RoleAuthority(savedRole.getId(), authority.getId())
                                )
                                .toList()
                        )
                );

    }


}
