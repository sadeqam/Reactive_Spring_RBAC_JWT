package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends R2dbcRepository<Book, Long> {

    Mono<Boolean> existsByName(String name);

}
