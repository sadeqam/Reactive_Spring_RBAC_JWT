package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {
    Mono<User> insert(User user);
}
