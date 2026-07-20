# Hands-on 1: Create a Spring Web Project using Maven

This document provides a walkthrough of the `spring-learn` project created under the `1. spring-rest-handson\Create a Spring Web Project using Maven` directory.

---

## 1. Project Directory Structure

A standard Maven-based Spring Boot project consists of the following key directories:

### 1.1 `src/main/java`
* **Purpose**: This folder contains all the application source code.
* **Details**: It contains the Java packages and classes. For this project, the package `com.cognizant.springlearn` houses the entry class [SpringLearnApplication.java](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/src/main/java/com/cognizant/springlearn/SpringLearnApplication.java).

### 1.2 `src/main/resources`
* **Purpose**: This folder is dedicated to application configuration, resources, templates, and static assets.
* **Details**: It includes [application.properties](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/src/main/resources/application.properties) (or `application.yml`), which defines runtime properties like server port, database connections, and logging levels.

### 1.3 `src/test/java`
* **Purpose**: This folder contains test source files, such as unit tests and integration tests.
* **Details**: It mirrors the packaging structure of `src/main/java` and includes classes like [SpringLearnApplicationTests.java](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/src/test/java/com/cognizant/springlearn/SpringLearnApplicationTests.java) to verify that the application context initializes correctly.

---

## 2. SpringLearnApplication.java Walkthrough

The entry point of the Spring Boot application is defined in [SpringLearnApplication.java](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/src/main/java/com/cognizant/springlearn/SpringLearnApplication.java):

```java
package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLearnApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Inside main method of SpringLearnApplication");
        SpringApplication.run(SpringLearnApplication.class, args);
        LOGGER.info("SpringLearnApplication started successfully!");
    }
}
```

### 2.1 The `main()` Method
* The `main()` method is the standard Java entry point of the application.
* **`SpringApplication.run(SpringLearnApplication.class, args)`**: This method bootstraps the Spring Application by performing the following steps:
  1. Creates an appropriate `ApplicationContext` instance depending on the classpath (a servlet-based web application in this case).
  2. Registers a `CommandLinePropertySource` to expose command-line arguments as Spring properties.
  3. Refreshes the application context, loading all singleton beans.
  4. Starts the embedded Tomcat servlet container on the default port `8080`.
* Custom SLF4J logging is included at the entry and exit of the bootstrapping phase to trace startup success.

### 2.2 Purpose of `@SpringBootApplication`
The `@SpringBootApplication` annotation is a convenience annotation that combines three essential annotations:
1. **`@SpringBootConfiguration`**: Inherits from `@Configuration` and designates the class as a source of bean definitions for the application context.
2. **`@EnableAutoConfiguration`**: Tells Spring Boot to automatically configure beans based on dependencies present on the classpath (e.g., configuring Tomcat and Spring Web MVC automatically because `spring-boot-starter-web` is declared in dependencies).
3. **`@ComponentScan`**: Tells Spring to scan the current package (`com.cognizant.springlearn`) and its sub-packages for stereotypic components, services, repositories, or controllers.

---

## 3. pom.xml Walkthrough

The Project Object Model ([pom.xml](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/pom.xml)) file manages project dependencies, plugins, and metadata:

* **`<parent>`**: Configures `spring-boot-starter-parent` as the parent POM. It provides default dependency management, pre-configured plugin versions, and sensible default compiler configuration.
* **`<groupId>`**: Identifies the organization (set to `com.cognizant`).
* **`<artifactId>`**: Unique identifier of the project (`spring-learn`).
* **`<properties>`**: Specifies compiler configuration (Java 21).
* **`<dependencies>`**:
  * `spring-boot-starter-web`: Pulls in all dependencies needed to build web applications, including RESTful endpoints, Spring MVC, and the embedded Tomcat container.
  * `spring-boot-devtools`: Provides fast application restarts, LiveReload support, and development-friendly property defaults.
  * `spring-boot-starter-test`: Includes testing libraries such as JUnit 5, Mockito, and AssertJ.
* **`<build>`**: Declares the `spring-boot-maven-plugin` which compiles the code and packages it into an executable JAR containing all dependencies.

---

## 4. Maven Dependency Tree

Running `mvn dependency:tree` displays the full hierarchy of libraries loaded by the Maven configuration:

```text
[INFO] com.cognizant:spring-learn:jar:0.0.1-SNAPSHOT
[INFO] +- org.springframework.boot:spring-boot-starter-web:jar:3.2.4:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter:jar:3.2.4:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:3.2.4:compile
[INFO] |  |  |  +- ch.qos.logback:logback-classic:jar:1.4.14:compile
[INFO] |  |  |  |  \- ch.qos.logback:logback-core:jar:1.4.14:compile
[INFO] |  |  |  +- org.apache.logging.log4j:log4j-to-slf4j:jar:2.21.1:compile
[INFO] |  |  |  |  \- org.apache.logging.log4j:log4j-api:jar:2.21.1:compile
[INFO] |  |  |  \- org.slf4j:jul-to-slf4j:jar:2.0.12:compile
[INFO] |  |  +- jakarta.annotation:jakarta.annotation-api:jar:2.1.1:compile
[INFO] |  |  \- org.yaml:snakeyaml:jar:2.2:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-json:jar:3.2.4:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.15.4:compile
[INFO] |  |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.15.4:compile
[INFO] |  |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.15.4:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.15.4:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.15.4:compile
[INFO] |  |  \- com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.15.4:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:3.2.4:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:10.1.19:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:10.1.19:compile
[INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:10.1.19:compile
[INFO] |  +- org.springframework:spring-web:jar:6.1.5:compile
[INFO] |  |  +- org.springframework:spring-beans:jar:6.1.5:compile
[INFO] |  |  \- io.micrometer:micrometer-observation:jar:1.12.4:compile
[INFO] |  |     \- io.micrometer:micrometer-commons:jar:1.12.4:compile
[INFO] |  \- org.springframework:spring-webmvc:jar:6.1.5:compile
[INFO] |     +- org.springframework:spring-aop:jar:6.1.5:compile
[INFO] |     +- org.springframework:spring-context:jar:6.1.5:compile
[INFO] |     \- org.springframework:spring-expression:jar:6.1.5:compile
[INFO] +- org.springframework.boot:spring-boot-devtools:jar:3.2.4:runtime
[INFO] |  +- org.springframework.boot:spring-boot:jar:3.2.4:compile
[INFO] |  \- org.springframework.boot:spring-boot-autoconfigure:jar:3.2.4:compile
[INFO] \- org.springframework.boot:spring-boot-starter-test:jar:3.2.4:test
[INFO]    +- org.springframework.boot:spring-boot-test:jar:3.2.4:test
[INFO]    +- org.springframework.boot:spring-boot-test-autoconfigure:jar:3.2.4:test
[INFO]    +- com.jayway.jsonpath:json-path:jar:2.9.0:test
[INFO]    |  \- org.slf4j:slf4j-api:jar:2.0.12:compile
[INFO]    +- jakarta.xml.bind:jakarta.xml.bind-api:jar:4.0.2:test
[INFO]    |  \- jakarta.activation:jakarta.activation-api:jar:2.1.3:test
[INFO]    +- net.minidev:json-smart:jar:2.5.0:test
[INFO]    |  \- net.minidev:accessors-smart:jar:2.5.0:test
[INFO]    |     \- org.ow2.asm:asm:jar:9.3:test
[INFO]    +- org.assertj:assertj-core:jar:3.24.2:test
[INFO]    |  \- net.bytebuddy:byte-buddy:jar:1.14.12:test
[INFO]    +- org.awaitility:awaitility:jar:4.2.0:test
[INFO]    +- org.hamcrest:hamcrest:jar:2.2:test
[INFO]    +- org.junit.jupiter:junit-jupiter:jar:5.10.2:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-api:jar:5.10.2:test
[INFO]    |  |  +- org.opentest4j:opentest4j:jar:1.3.0:test
[INFO]    |  |  +- org.junit.platform:junit-platform-commons:jar:1.10.2:test
[INFO]    |  |  \- org.apiguardian:apiguardian-api:jar:1.1.2:test
[INFO]    |  +- org.junit.jupiter:junit-jupiter-params:jar:5.10.2:test
[INFO]    |  \- org.junit.jupiter:junit-jupiter-engine:jar:5.10.2:test
[INFO]    |     \- org.junit.platform:junit-platform-engine:jar:1.10.2:test
[INFO]    +- org.mockito:mockito-core:jar:5.7.0:test
[INFO]    |  +- net.bytebuddy:byte-buddy-agent:jar:1.14.12:test
[INFO]    |  \- org.objenesis:objenesis:jar:3.3:test
[INFO]    +- org.mockito:mockito-junit-jupiter:jar:5.7.0:test
[INFO]    +- org.skyscreamer:jsonassert:jar:1.5.1:test
[INFO]    |  \- com.vaadin.external.google:android-json:jar:0.0.20131108.vaadin1:test
[INFO]    +- org.springframework:spring-core:jar:6.1.5:compile
[INFO]    |  \- org.springframework:spring-jcl:jar:6.1.5:compile
[INFO]    +- org.springframework:spring-test:jar:6.1.5:test
[INFO]    \- org.xmlunit:xmlunit-core:jar:2.9.1:test
```
