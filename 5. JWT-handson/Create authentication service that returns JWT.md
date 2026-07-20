# Create Authentication Service that Returns JWT

This document walkthrough describes the implementation of a Spring Boot JWT Authentication Service under `5. JWT-handson\Create authentication service that returns JWT`.

---

## 1. Configuration & Dependencies (`pom.xml`)

We added the following dependencies to support security and JWT creation:
* **`spring-boot-starter-security`**: Secures endpoints and handles authentication filters.
* **`jjwt-api` / `jjwt-impl` / `jjwt-jackson` (Version `0.11.5`)**: Provides JWT creation, parsing, and signing capabilities using HMAC-SHA (HS256).

---

## 2. Spring Security Configuration (`SecurityConfig.java`)

We configured Spring Security in [SecurityConfig.java](file:///C:/Users/ram/Documents/cognizant/5.%20JWT-handson/Create%20authentication%20service%20that%20returns%20JWT/spring-learn/src/main/java/com/cognizant/springlearn/security/SecurityConfig.java):
```java
package com.cognizant.springlearn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```
### Key Aspects:
* **`permitAll()` for `/authenticate`**: Allows unauthenticated requests to reach our controller, where we manually inspect the Basic Auth headers.
* **`csrf().disable()`**: Disables Cross-Site Request Forgery security checks to facilitate easy API testing.

---

## 3. JWT Token Generation Utility (`JwtUtil.java`)

The [JwtUtil.java](file:///C:/Users/ram/Documents/cognizant/5.%20JWT-handson/Create%20authentication%20service%20that%20returns%20JWT/spring-learn/src/main/java/com/cognizant/springlearn/security/JwtUtil.java) utility handles generation of standard HMAC-SHA256 signed tokens:
```java
package com.cognizant.springlearn.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String SECRET_KEY_STRING = "mySecretKeyForJWTSecurityMustBeAtLeast32BytesLong!";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    private static final long EXPIRATION_TIME_MS = 20 * 60 * 1000; // 20 minutes

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
            .compact();
    }
}
```

---

## 4. Auth Controller (`AuthController.java`)

The controller class [AuthController.java](file:///C:/Users/ram/Documents/cognizant/5.%20JWT-handson/Create%20authentication%20service%20that%20returns%20JWT/spring-learn/src/main/java/com/cognizant/springlearn/controller/AuthController.java) exposes the GET `/authenticate` endpoint and performs manual Base64 Basic Auth header parsing:

```java
package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            try {
                // Decode the credentials from Base64
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                byte[] decoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decoded, StandardCharsets.UTF_8);
                
                String[] values = credentials.split(":", 2);
                if (values.length == 2) {
                    String username = values[0];
                    String password = values[1];

                    // Verify credentials
                    if ("user".equals(username) && "pwd".equals(password)) {
                        String token = jwtUtil.generateToken(username);
                        return ResponseEntity.ok(Collections.singletonMap("token", token));
                    }
                }
            } catch (Exception e) {
                // Handle decode exception ...
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
```

---

## 5. Verification Response

* **Command**:
  `curl -s -u user:pwd http://localhost:8090/authenticate`
* **Response**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg0NTU3NzY2LCJleHAiOjE3ODQ1NTg5NjZ9.d0VUy8nFZ1XU9Domx42GA7WUAItItw4AFwgYQ3mXxKU"
  }
  ```
This confirms that the JWT Authentication Service successfully decodes Basic Auth credentials and responds with a signed token.
