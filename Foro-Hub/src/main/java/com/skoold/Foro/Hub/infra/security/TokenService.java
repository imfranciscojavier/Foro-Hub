package com.skoold.Foro.Hub.infra.security;

import com.skoold.Foro.Hub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apisecret;

    // Método para generar el token
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apisecret);
            return JWT.create()
                    .withIssuer("foro hub")
                    .withSubject(usuario.getCorreo_electronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion()) // Usamos el método aquí
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al crear el token", exception);
        }
    }

    // Método para obtener el sujeto del token
    public String getSubject(String token){
        if(token == null || token.trim().isEmpty()){
            throw new RuntimeException("Token no proporcionado");
        }

        // Verificar que el token tenga tres partes (header, payload, signature)
        if (token.split("\\.").length != 3) {
            throw new RuntimeException("Token mal formado. Debe tener 3 partes.");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apisecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("foro hub")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido: " + exception.getMessage(), exception);
        }

        String subject = verifier.getSubject();
        if(subject == null || subject.isEmpty()){
            throw new RuntimeException("El sujeto del token no es válido");
        }
        return subject;
    }

    // Método para generar la fecha de expiración
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}


/* import com.skoold.Foro.Hub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apisecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apisecret);
            return JWT.create()
                    .withIssuer("foro hub")
                    .withSubject(usuario.getCorreo_electronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("");
        }
    }

    public String getSubject(String token){
        if(token == null){
            throw new RuntimeException("");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apisecret);
            verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("foro hub")
                    // reusable verifier instance
                    .build()
                    .verify(token);
            verifier.getSubject();

        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            System.out.println(exception.toString());
        }
        if(verifier.getSubject() == null){
            throw new RuntimeException("Verifie Invalido");
        }
        return verifier.getSubject();
    }
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
} */