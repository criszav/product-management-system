package com.czavala.productmanagementsystem.services.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.expiration_in_minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret_key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        // fecha de emision del token en milisegundos
        Date issuedAt = new Date(System.currentTimeMillis());
        // fecha de expiracion corresponde a fecha de emision mas los minutos de expiracion del token segun constante "EXPIRATION_IN_MINUTES"
        Date expirationDate = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        String jwt = Jwts.builder()

                // agrega claims adicionales al payload del token
                .claims(extraClaims)

                // especifica el tipo de token en el header del token
                .header()
                    .type("JWT")
                .and()

                // agrega username (subject) al payload del token
                .subject(user.getUsername())

                // especifica fecha de emision del token
                .issuedAt(issuedAt)

                // especifica fecha de expiracion del token
                .expiration(expirationDate)

                // especifica la firma del token (algoritmo de firma)
                .signWith(generateKey(), Jwts.SIG.HS256)

                .compact();

        return jwt;
    }

    private SecretKey generateKey() {

        // decodifica nuestra secre_key, la cual esta codificada en base 64
        byte[] decodedKey = Decoders.BASE64.decode(SECRET_KEY);

        // esto servía cuando nuestra clave NO estaba codificada en base 64
//        byte[] key = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        // extrae username (subject) del jwt
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload(); // obtiene la data (claims) del jwt
    }
}
