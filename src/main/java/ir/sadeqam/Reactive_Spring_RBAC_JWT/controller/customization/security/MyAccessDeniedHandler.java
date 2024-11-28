package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
public class MyAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Autowired
    @Qualifier("globalExceptionHandler")
    WebExceptionHandler exceptionHandler;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return exceptionHandler.handle(exchange, denied);
    }

}
