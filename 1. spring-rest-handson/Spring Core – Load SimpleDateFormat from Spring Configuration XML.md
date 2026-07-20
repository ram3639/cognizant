# Hands-on 2: Spring Core – Load SimpleDateFormat from Spring Configuration XML

This document walks through configuring and loading a `java.text.SimpleDateFormat` bean defined in a Spring XML configuration file within a Spring Boot application.

---

## 1. Spring XML Configuration File (`date-format.xml`)

We created the XML configuration file `date-format.xml` in the `src/main/resources` folder:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Define a bean for SimpleDateFormat with a date format pattern -->
    <bean id="dateFormat" class="java.text.SimpleDateFormat">
        <constructor-arg value="dd/MM/yyyy" />
    </bean>

</beans>
```

### Explanation:
* **`<bean id="dateFormat" class="java.text.SimpleDateFormat">`**: Tells Spring container to instantiate an object of `java.text.SimpleDateFormat` and manage it as a bean named `dateFormat`.
* **`<constructor-arg value="dd/MM/yyyy" />`**: Passes `"dd/MM/yyyy"` as the constructor argument to the `SimpleDateFormat` constructor.

---

## 2. Retrieving and Using the Bean in code

Inside [SpringLearnApplication.java](file:///C:/Users/ram/Documents/cognizant/1.%20spring-rest-handson/Create%20a%20Spring%20Web%20Project%20using%20Maven/spring-learn/src/main/java/com/cognizant/springlearn/SpringLearnApplication.java), the new method `displayDate()` fetches the bean and parses a test date:

```java
    public static void displayDate() {
        LOGGER.info("START displayDate");
        
        // Initialize ApplicationContext by reading the XML configuration from classpath
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");
        
        // Retrieve the bean by ID and cast it to SimpleDateFormat
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
        
        try {
            // Parse and display the date
            Date date = format.parse("31/12/2018");
            System.out.println("Parsed Date: " + date);
        } catch (Exception e) {
            LOGGER.error("Error parsing date", e);
        }
        
        LOGGER.info("END displayDate");
    }
```

### Output:
When the application runs, the following is outputted to the console log:
```text
Parsed Date: Mon Dec 31 00:00:00 IST 2018
```
This confirms that the `SimpleDateFormat` bean is correctly loaded and configured by Spring's `ClassPathXmlApplicationContext`.
