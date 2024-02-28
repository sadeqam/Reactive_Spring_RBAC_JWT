package ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    Mono<Boolean> existsByName(String name);

}
