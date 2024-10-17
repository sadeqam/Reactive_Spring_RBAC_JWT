package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.BookRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InternalBookService implements BookService {

    @Autowired
    BookRepository repository;

    @Override
    public Mono<Book> insert(Book book) {
        return repository.save(book);
    }

    @Override
    public Mono<Book> find(Long id) {
        return repository.findById(id).switchIfEmpty(
                Mono.error(new DataNotFoundException("book with id %d not found".formatted(id)))
        );
    }

    @Override
    public Flux<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Boolean> isExists(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Void> remove(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Book> update(Book book) {
        return repository.save(book); // save check id ( if exist : update,  if not : insert) 🤢
    }

}
