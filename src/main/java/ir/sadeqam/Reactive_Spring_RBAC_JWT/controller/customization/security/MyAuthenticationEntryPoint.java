package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
public class MyAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Autowired
    @Qualifier("globalExceptionHandler")
    WebExceptionHandler exceptionHandler;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return exceptionHandler.handle(exchange, ex);
    }
}
