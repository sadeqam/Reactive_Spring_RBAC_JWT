package ir.sadeqam.Reactive_Spring_RBAC_JWT;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Authority;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Book;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.Role;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.repository.entities.User;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.List;


@SpringBootApplication
public class ReactiveSpringRbacJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringRbacJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner run(BookService bookService, UserService userService,
                          RoleService roleService, AuthorityService authorityService) {
        return args -> {
            if (Boolean.TRUE.equals(bookService.isExists("DataBase 101").block())) return;

            bookService.insert(new Book("DataBase 101", "alireza alizadeh")).block();
            bookService.insert(new Book("Hardware 101", "mohammad mohamadzadeh")).block();

            var select = authorityService.insert(new Authority("select")).block();
            var create = authorityService.insert(new Authority("insert")).block();
            var change = authorityService.insert(new Authority("change")).block();
            var remove = authorityService.insert(new Authority("remove")).block();


            var adminAuthorities = List.of(select, create, change, remove);
            var userAuthorities = List.of(select, create);

            var admin = roleService.insert(new Role("ADMIN", adminAuthorities)).block();
            var user = roleService.insert(new Role("USER", userAuthorities)).block();

            var users = List.of(
                    new User("sadeq", "sadeq", List.of(admin, user)),
                    new User("mamad", "mamad", List.of(user))
            );

            userService.insert(new User("sadeq", "sadeq", List.of(admin, user))).block();
            userService.insert(new User("mamad", "mamad", List.of(user))).block();


//            var n = bookService.insertAll(Flux.just(
//                    new Book("DataBase 101", "alireza alizadeh"),
//                    new Book("Hardware 101", "mohammad mohamadzadeh"))
//            ).collectList().block();
//
//            var step1 =
//                    bookService.insertAll(Flux.just(
//                                    new Book("DataBase 101", "alireza testzade"),
//                                    new Book("Hardware 101", "mohammad springabadi"))
//                            )
//                            .thenMany(Flux.just(
//                                    new Authority("select"),
//                                    new Authority("insert"),
//                                    new Authority("change"),
//                                    new Authority("remove"))
//                            )
//                            .flatMap(authorityService::insert)
//                            .collectList()
//                            .flatMap(authorities ->
//                                    Mono.just(List.of(
//                                            new Role("admin", authorities),
//                                            new Role("user", authorities.subList(0, 2))
//                                    ))
//                            );
//
//            var step2 = authorityService.insertAll(
//                    Flux.just(
//                            new Authority("select"),
//                            new Authority("insert"),
//                            new Authority("change"),
//                            new Authority("remove")
//                    )
//            );
//
//            var x = Mono.zip();


        };
    }
}
