# Hello World RESTful Web Service

This document provides a walkthrough of the Hello World RESTful Web Service created under `2. spring-rest-handson\Hello World RESTful Web Service`.

---

## 1. Controller Code (`HelloController.java`)

We created the REST controller class [HelloController.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/Hello%20World%20RESTful%20Web%20Service/spring-learn/src/main/java/com/cognizant/springlearn/controller/HelloController.java) under the package `com.cognizant.springlearn.controller`:

```java
package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("START sayHello");
        String response = "Hello World!!";
        LOGGER.info("END sayHello");
        return response;
    }
}
```

### Key Technical Aspects:
1. **`@RestController`**: Combines `@Controller` and `@ResponseBody`. It marks the class as a web controller and automatically serializes the returned value directly into the HTTP response body.
2. **`@GetMapping("/hello")`**: Maps HTTP GET requests on the `/hello` path to the `sayHello()` method.
3. **Start/End Logging**: Custom SLF4J logs record when execution starts and completes in the console.

---

## 2. Package Name Correction
The original instruction suggested the package name `com.cognizant.spring-learn.controller`. 
* **Note**: Package names in Java cannot contain hyphens (`-`) because they are treated as subtraction operators, causing syntax errors. We corrected the package path to `com.cognizant.springlearn.controller`.

---

## 3. HTTP Header Analysis

When requesting `http://localhost:8083/hello` in Google Chrome (Network Tab) or Postman (Headers Tab), the following headers are received from the server:

| Header Name | Sample Value | Explanation |
| :--- | :--- | :--- |
| **`Content-Type`** | `text/plain;charset=UTF-8` | Indicates that the content is plain text formatted using UTF-8 character encoding. |
| **`Content-Length`** | `13` | The length of the response body in bytes (matches the string length of `"Hello World!!"`). |
| **`Date`** | `Mon, 20 Jul 2026 14:14:26 GMT` | The date and time when the HTTP response was generated. |
| **`Connection`** | `keep-alive` | Tells the client that the server wishes to keep the TCP connection open for subsequent requests. |
| **`Keep-Alive`** | `timeout=60` | Specifies connection timeout settings (the connection stays idle for up to 60 seconds before closing). |

### Viewing Headers:
* **Chrome**: Open Developer Tools (`F12`), go to the **Network** tab, request the URL, click on the `/hello` resource, and view **Headers > Response Headers**.
* **Postman**: Send the request, scroll to the response section, and click on the **Headers** tab to view the table of headers.
