package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// be careful about filter auto registration (don't use {at} component here)
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtHandler jwtHandler;

    public JwtAuthenticationFilter(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            return chain.filter(exchange);
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            var decoded = jwtHandler.verify(token);
            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    decoded.getSubject(),
                    null,
                    AuthorityUtils.createAuthorityList(decoded.getClaim("authorities").asList(String.class))
            );
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        } catch (JWTVerificationException jwtVerificationException) {
            // wrap JWTVerificationException in subclass of AuthenticationException
            // for use ExceptionTranslationFilter and AuthenticationEntryPoint to
            // handling this kind of exception in global Exception Handler class (controller.customization)
            throw new TokenVerificationException(jwtVerificationException.getMessage());
        }
    }

}
