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
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean // not use HttpSecurity and SecurityFilterChain in reactive stack !!
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) {
        return security
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
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
        // there is no authentication provider in reactive stack
        // authentication manager directly access to user details service !!???
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtHandler getJwtHandler() {
        return new JwtHandler();
    }


}
