# REST - Country Web Service

This document provides a walkthrough of the Country REST Web Service implemented under `2. spring-rest-handson\REST - Country Web Service`.

---

## 1. Code Components

### 1.1 Model Class (`Country.java`)
We created the [Country.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Country%20Web%20Service/spring-learn/src/main/java/com/cognizant/springlearn/Country.java) class to model a country:
```java
package com.cognizant.springlearn;

public class Country {
    private String code;
    private String name;

    public Country() {}

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters and Setters ...
}
```

### 1.2 Spring Configuration XML (`country.xml`)
The country definitions are configured in [country.xml](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Country%20Web%20Service/spring-learn/src/main/resources/country.xml):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="in" class="com.cognizant.springlearn.Country">
        <property name="code" value="IN" />
        <property name="name" value="India" />
    </bean>

</beans>
```

### 1.3 Controller Class (`CountryController.java`)
The endpoint is mapped in [CountryController.java](file:///C:/Users/ram/Documents/cognizant/2.%20spring-rest-handson/REST%20-%20Country%20Web%20Service/spring-learn/src/main/java/com/cognizant/springlearn/controller/CountryController.java):
```java
package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public Country getCountryIndia() {
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        return context.getBean("in", Country.class);
    }
}
```

---

## 2. SME Walkthrough Questions

### 2.1 What happens in the controller method?
1. The method `getCountryIndia()` is executed when a GET request is sent to `/country`.
2. It initializes a new `ClassPathXmlApplicationContext` by loading `country.xml` from the classpath.
3. It requests the bean named `"in"` of type `Country.class` from the Spring container.
4. The container retrieves the configured singleton `Country` instance representing India.
5. The method returns this POJO (Plain Old Java Object) back to Spring's DispatcherServlet.

### 2.2 How the bean is converted into a JSON response?
1. The `@RestController` annotation is applied at the class level. This implicitly applies `@ResponseBody` to all controller methods.
2. When a method returns a Java object (e.g. `Country`), Spring MVC's content negotiation mechanism selects the appropriate `HttpMessageConverter` from the classpath.
3. Because `spring-boot-starter-web` automatically registers the **Jackson JSON processing library** on the classpath, Spring configures and executes `MappingJackson2HttpMessageConverter`.
4. Jackson serializes the POJO's properties (`code` and `name`) by matching their getter methods (`getCode()`, `getName()`) to produce a standard JSON string: `{"code":"IN","name":"India"}`.
5. The serialized JSON string is written directly into the body of the HTTP response.

### 2.3 HTTP Headers Received
When querying `http://localhost:8083/country` in Google Chrome (Network Tab) or Postman (Headers Tab), the headers received include:

* **`Content-Type: application/json`**: This header is critical as it informs the client browser/tool that the response body is formatted as a JSON object, prompting the client to parse it as JSON rather than raw text.
* **`Transfer-Encoding: chunked`**: Indicates that the server is transferring the response in dynamic chunks instead of sending a predefined `Content-Length`.
* **`Connection: keep-alive`**: Requests the TCP connection be held open for subsequent requests.
