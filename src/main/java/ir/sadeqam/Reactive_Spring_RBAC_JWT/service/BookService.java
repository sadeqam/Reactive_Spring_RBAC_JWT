package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {


    Mono<Book> insert(Book book);
    Mono<Book> find(Long id);
    Flux<Book> findAll();
    Mono<Boolean> isExists(String name);
    Mono<Void> remove(Long id);
    Mono<Book> update(Book book);
}
