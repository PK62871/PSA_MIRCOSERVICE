package com.authservice.trial;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryptTrial {

    //Password Encoder by BCryptPasswordEncoder.........
    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encoded = passwordEncoder.encode("Prabhakar");
        System.out.println(encoded);
    }
}
