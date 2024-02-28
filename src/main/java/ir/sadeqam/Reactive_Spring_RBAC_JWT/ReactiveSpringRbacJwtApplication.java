package ir.sadeqam.Reactive_Spring_RBAC_JWT;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveSpringRbacJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringRbacJwtApplication.class, args);
    }

    @Bean //TODO just for testing, remove it in production 🤨
    CommandLineRunner run(BookService bookService) {
        return args -> {

        };
    }
}
