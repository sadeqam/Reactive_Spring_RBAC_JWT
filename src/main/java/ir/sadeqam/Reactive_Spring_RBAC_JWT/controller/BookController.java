package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
        path = "api/books",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
)
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    public Flux<Book> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Book> find(@PathVariable Long id){
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> insert(@Valid @RequestBody Book book){
        return service.insert(book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> remove(@PathVariable Long id){
        return service.remove(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> update(@Valid @RequestBody Book book){
        return service.update(book);
    }

}
