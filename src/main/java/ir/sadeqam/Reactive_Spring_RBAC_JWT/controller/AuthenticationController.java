package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller;


import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.JwtHandler;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.UserAuthenticationInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        path = "api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthenticationController {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    private final JwtHandler jwtHandler;

    public AuthenticationController(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, String>> login(@Valid @RequestBody UserAuthenticationInfo info) {

        return Mono.just(new UsernamePasswordAuthenticationToken(info.getUsername(), info.getPassword()))
                .flatMap(auth -> reactiveAuthenticationManager.authenticate(auth))
                .map(Authentication::getAuthorities)
                .map(grantedAuthorities -> grantedAuthorities
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new)
                )
                .map(authoritiesArray -> jwtHandler.generate(info.getUsername(), authoritiesArray))
                .map(jwt -> Map.of("token", jwt));

    }
}
