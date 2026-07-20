package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(HttpServletRequest request) {
        LOGGER.info("START authenticate");
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            try {
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                byte[] decoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decoded, StandardCharsets.UTF_8);
                
                String[] values = credentials.split(":", 2);
                if (values.length == 2) {
                    String username = values[0];
                    String password = values[1];

                    LOGGER.info("Decoded credentials: username='{}'", username);

                    if ("user".equals(username) && "pwd".equals(password)) {
                        String token = jwtUtil.generateToken(username);
                        LOGGER.info("Authentication successful. Generated token.");
                        LOGGER.info("END authenticate");
                        return ResponseEntity.ok(Collections.singletonMap("token", token));
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error decoding credentials", e);
            }
        }

        LOGGER.warn("Authentication failed or Authorization header missing/invalid.");
        LOGGER.info("END authenticate");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
