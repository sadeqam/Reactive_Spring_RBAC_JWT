package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.BookService;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.InternalBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
        path = "api/books",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping
    @PreAuthorize("hasAuthority('select')")
    public Flux<Book> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('select')")
    public Mono<Book> find(@PathVariable Long id){
        return service.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('insert')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> insert(@Valid @RequestBody Book book){
        return service.insert(book);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('remove')")
    public Mono<Void> remove(@PathVariable Long id){
        return service.remove(id);
    }

    @PreAuthorize("hasAuthority('change')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> update(@Valid @RequestBody Book book){
        return service.update(book);
    }

}
