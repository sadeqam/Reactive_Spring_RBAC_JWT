package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.AuthorityRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DefaultAuthorityService implements AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    @Override
    public Mono<Authority> insert(Authority authority) {
        return repository.save(authority);
    }

    @Override
    public Mono<Authority> find(Long id) {
        return repository.findById(id).switchIfEmpty(
                Mono.error(new DataNotFoundException("authority with id %d not found".formatted(id)))
        );
    }

    @Override
    public Mono<Authority> findByAuthority(String authority) {
        return repository.findByAuthority(authority).switchIfEmpty(
                Mono.error(new DataNotFoundException("Authority with name %s not found".formatted(authority)))
        );
    }


}
