package ir.sadeqam.Reactive_Spring_RBAC_JWT.service;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.repositories.BookRepository;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public Mono<Book> insert(Book book) {
        return repository.save(book);
    }

    public Mono<Book> find(Long id) {
        return repository.findById(id);
    }

    public Flux<Book> findAll() {
        return repository.findAll();
    }

    public Mono<Boolean> isExists(String name) {
        return repository.existsByName(name);
    }

    public Mono<Void> remove(Long id) {
        return repository.deleteById(id);
    }

    public Mono<Book> update(Book book) {
        return repository.save(book); // save check id ( if exist : update,  if not : insert) 🤢
    }

}
