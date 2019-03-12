<p align="center">
  <a href="https://spring.io/projects/spring-boot">
    <img src="https://spring.io/img/homepage/icon-spring-boot.svg" alt="Logo" width=72 height=72>
  </a>

  <h3 align="center">RestFul API with Java 8 and SpringBoot 2</h3>

  <p align="center">
    This example app shows how to build a basic RestFul API with Java 8, Spring Boot 2 and PostgreSQL.
  </p>
</p>

## Author

* **Rafael Lima**  - [Linkedin](https://www.linkedin.com/in/rafaeldblima/)

## Quick start

1. Clone the project
 ```bash
 git clone https://github.com/rafaeldblima/senior-service.git
 ```

2. Go inside project folder:
 ```bash
 cd senior-service
 ```

3. Now we have two ways:
   1. Development
   
      **Warning**
      
      > Must have a postgres server installed with login and password **postgres** and a database called **xptodatabase** on port **5432**
  
      1. Prepare PostgreSQL
   
      **Warning**
      
      > Must have Java and Maven installed
      2. Launch development server
  
       ```bash
       mvn spring-boot:run 
       ```
  
      3. Open browser
  
       ```bash
       http://localhost:8080/swagger-ui.html
       ```
       All endpoints can be tested here
   2. Docker-compose
   
      **Warning**
      
      > Must have docker and docker-compose installed
      1. Go inside infra folder
  
       ```bash
       cd infra
       ```
  
      2. Launch docker
   
      **Warning**
      
      > build can take a while, please wait
  
       ```bash
       docker-compose up
       ```
  
      3. Open browser
   
      **Warning**
      
      > port 8080 must be free to be used
  
       ```bash
       http://localhost:8080/swagger-ui.html
       ```
       All endpoints can be tested here

## What's included

* Java 8
* SpringBoot 2;
* Swagger;
* jUnit.
