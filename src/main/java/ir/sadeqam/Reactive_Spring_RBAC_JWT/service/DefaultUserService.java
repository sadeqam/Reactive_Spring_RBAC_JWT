package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Role;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.User;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.UserRole;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.UserRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.UserRoleRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public Mono<User> insert(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user)
                .delayUntil(savedUser -> userRoleRepository.saveAll(
                                user.getRoles()
                                        .stream().map(
                                                role -> new UserRole(savedUser.getId(), role.getId())
                                        )
                                        .toList()
                        )
                );
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("username not found")))
                .flatMap(user -> findAllRoles(user.getId())
                        .collectList()
                        .map(roles ->
                                new User(
                                        user.getId(),
                                        user.getUsername(),
                                        user.getPassword(),
                                        user.isAccountNonExpired(),
                                        user.isAccountNonLocked(),
                                        user.isCredentialsNonExpired(),
                                        user.isEnabled(),
                                        roles
                                )
                        )
                );
    }

    private Flux<Role> findAllRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(
                        new DataNotFoundException("no role found for this user (user id is %d)".formatted(userId))
                ))
                .flatMap(userRole -> roleService.find(userRole.getRoleId()));
    }

}
