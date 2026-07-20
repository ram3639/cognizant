# REST - Get country based on country code

This document provides a walkthrough of the Get Country by Code REST Web Service implemented under `2. spring-rest-handson\REST - Get country based on country code`.

---

## 1. Code Components

### 1.1 Service Class (`CountryService.java`)
The core searching logic is implemented in [CountryService.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Get%20country%20based%20on%20country%20code/spring-learn/src/main/java/com/cognizant/springlearn/service/CountryService.java):
```java
package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.exception.CountryNotFoundException;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @SuppressWarnings("unchecked")
    public Country getCountry(String code) throws CountryNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);
        
        return countries.stream()
            .filter(c -> c.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new CountryNotFoundException("Country not found with code: " + code));
    }
}
```

### 1.2 Exception Class (`CountryNotFoundException.java`)
To map missing codes to an HTTP 404 response, we created [CountryNotFoundException.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Get%20country%20based%20on%20country%20code/spring-learn/src/main/java/com/cognizant/springlearn/exception/CountryNotFoundException.java):
```java
package com.cognizant.springlearn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Country not found")
public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String message) {
        super(message);
    }
}
```

### 1.3 Controller Class (`CountryController.java`)
The mappings are defined in [CountryController.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Get%20country%20based%20on%20country%20code/spring-learn/src/main/java/com/cognizant/springlearn/controller/CountryController.java):
```java
package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.exception.CountryNotFoundException;
import com.cognizant.springlearn.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping({"/countries/{code}", "/country/{code}"})
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        return countryService.getCountry(code);
    }
}
```

---

## 2. SME Walkthrough Questions

### 2.1 How is the country code extracted?
* **`@PathVariable`**: The `@PathVariable` annotation binds the URL template variable `{code}` (from `@GetMapping`) directly to the method parameter `String code`.
* **Flexible URL Mapping**: By passing an array to the `@GetMapping` annotation (`{"/countries/{code}", "/country/{code}"}`), the application handles requests using both singular and plural forms gracefully.

### 2.2 Case-Insensitive Matching
* Instead of iterating the list using standard loops, we used a **Java Stream**:
  1. `countries.stream()` converts the list into a stream of `Country` objects.
  2. `.filter(c -> c.getCode().equalsIgnoreCase(code))` filters the stream elements where the country code matches the path variable, ignoring the case (e.g. `"in"`, `"IN"`, and `"In"` are all matched identically).
  3. `.findFirst()` retrieves the first match.
  4. `.orElseThrow(...)` throws a `CountryNotFoundException` if no match is found, causing Spring to send an **HTTP 404 (Not Found)** status back to the client.

### 2.3 Verification Requests
* `GET http://localhost:8083/country/in` -> Returns `200 OK` with body `{"code":"IN","name":"India"}`.
* `GET http://localhost:8083/countries/US` -> Returns `200 OK` with body `{"code":"US","name":"United States"}`.
* `GET http://localhost:8083/country/xx` -> Returns `404 Not Found`.
