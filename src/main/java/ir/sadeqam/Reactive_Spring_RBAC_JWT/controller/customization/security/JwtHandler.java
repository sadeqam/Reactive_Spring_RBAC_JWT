package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHandler {

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;

    @Value("${app.security.jwt.issuer}")
    private String issuer;

    @Value("${app.security.jwt.expire-after}")
    private Long expireAfter;

    public String generate(String username, String[] authorities) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withArrayClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expireAfter))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public DecodedJWT verify(String jwt) {
        var verifier = JWT.require(Algorithm.HMAC256(secretKey)).withIssuer(issuer).build();
        return verifier.verify(jwt); // JWTVerificationException handled in controller.customization.MyExceptionHandler
    }

}
