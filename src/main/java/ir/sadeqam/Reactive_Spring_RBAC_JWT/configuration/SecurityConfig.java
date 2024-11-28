package ir.sadeqam.Reactive_Spring_RBAC_JWT.configuration;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.JwtAuthenticationFilter;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.JwtHandler;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.MyAccessDeniedHandler;
import ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;


    @Bean // not use HttpSecurity and SecurityFilterChain in reactive stack !!
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) {
        var x = security
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // check session management ?????
                .requestCache(ServerHttpSecurity.RequestCacheSpec::disable)
                .addFilterAfter(
                        new JwtAuthenticationFilter(getJwtHandler()),
                        SecurityWebFiltersOrder.EXCEPTION_TRANSLATION
                )
                .authorizeExchange(exchangeSpec -> {
                    exchangeSpec.pathMatchers("api/books").authenticated();
                    exchangeSpec.pathMatchers("api/books/*").authenticated();
                    exchangeSpec.anyExchange().permitAll();
                })
                .exceptionHandling(exceptionHandlingSpec -> {
                    exceptionHandlingSpec.accessDeniedHandler(myAccessDeniedHandler);
                    exceptionHandlingSpec.authenticationEntryPoint(myAuthenticationEntryPoint);
                })
                .build();
        System.out.println(x.getWebFilters().collectList().block());

        return x;
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
        // there is no authentication provider in reactive stack
        // authentication manager directly access to user details service !!???
        var manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //check better way to do this 🤮, it seems to be really bad idea !!
    @Bean
    public JwtHandler getJwtHandler() {
        return new JwtHandler();
    }


}
