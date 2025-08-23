package com.authservice.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    //SECRET and EXPIRY_TIME4
    private static final String SECRET_KEY = "my-super-secret-key";
    private static final long EXPIRATION_TIME = 86400000; // 1 day



    //Call this method to generate token once username and password is correct..........
    public String generateJwtToken(String username,String role){
        return JWT.create()
                .withSubject(username)
                .withClaim("role",role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));

    }

    // Method to validate the token........... and retrieve the username....
    public String validateTokenAndRetrieveSubject(String token){
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }
}
