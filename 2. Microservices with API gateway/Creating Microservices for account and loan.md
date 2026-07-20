# Creating Microservices for Account and Loan

This document provides a walkthrough of the account and loan microservices created under `2. Microservices with API gateway\Creating Microservices for account and loan`.

---

## 1. Concept of Independent Microservices
A microservices architecture splits a large system into smaller, self-contained service units. In this exercise:
* **Account Microservice**: Focuses solely on bank account details.
* **Loan Microservice**: Focuses solely on loan configurations.
* Both services run as independent Spring Boot projects with their own classpath, `pom.xml`, and configurations.

---

## 2. Account Microservice Implementation
* **Port**: `8080` (configured in [application.properties](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/account/src/main/resources/application.properties))
* **Application Class**: [AccountApplication.java](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/account/src/main/java/com/cognizant/account/AccountApplication.java)
* **Controller Endpoint**: `GET /accounts/{number}`
* **Implementation** ([AccountController.java](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/account/src/main/java/com/cognizant/account/controller/AccountController.java)):
  ```java
  @RestController
  public class AccountController {
      @GetMapping("/accounts/{number}")
      public Map<String, Object> getAccount(@PathVariable String number) {
          Map<String, Object> account = new HashMap<>();
          account.put("number", number);
          account.put("type", "savings");
          account.put("balance", 234343);
          return account;
      }
  }
  ```

---

## 3. Loan Microservice Implementation
* **Port**: `8081` (configured in [application.properties](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/loan/src/main/resources/application.properties))
* **Application Class**: [LoanApplication.java](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/loan/src/main/java/com/cognizant/loan/LoanApplication.java)
* **Controller Endpoint**: `GET /loans/{number}`
* **Implementation** ([LoanController.java](file:///C:/Users/ram/Documents/cognizant/2.%20Microservices%20with%20API%20gateway/Creating%20Microservices%20for%20account%20and%20loan/loan/src/main/java/com/cognizant/loan/controller/LoanController.java)):
  ```java
  @RestController
  public class LoanController {
      @GetMapping("/loans/{number}")
      public Map<String, Object> getLoan(@PathVariable String number) {
          Map<String, Object> loan = new HashMap<>();
          loan.put("number", number);
          loan.put("type", "car");
          loan.put("loan", 400000);
          loan.put("emi", 3258);
          loan.put("tenure", 18);
          return loan;
      }
  }
  ```

---

## 4. Port Conflict and Resolution
* **The Problem**: If both microservices are launched with their default ports, the second service fails to bind with an error:
  `java.net.BindException: Address already in use`
  This happens because Tomcat defaults to port `8080` for both projects.
* **The Resolution**: By adding `server.port=8081` to the Loan service properties, they can run concurrently on the same host machine.

---

## 5. Verification Results
Both services were launched simultaneously and queried successfully:
* **Querying Account (Port 8080)**:
  `GET http://localhost:8080/accounts/00987987973432`
  Response:
  ```json
  {"number":"00987987973432","balance":234343,"type":"savings"}
  ```
* **Querying Loan (Port 8081)**:
  `GET http://localhost:8081/loans/H00987987972342`
  Response:
  ```json
  {"emi":3258,"number":"H00987987972342","loan":400000,"type":"car","tenure":18}
  ```
