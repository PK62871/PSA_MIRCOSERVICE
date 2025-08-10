🌐 What are Microservices?
################################
Microservices is an architecture style where an application is broken down into small, independent services that communicate over a network (usually HTTP). Each service focuses on a single business function, is independently deployable, and can be developed, scaled, and maintained separately.


🚀 Why Use Spring Boot for Microservices?
###########################################
Spring Boot provides all the necessary tools and integrations (e.g., REST APIs, Eureka, Config Server, etc.) to build robust, production-ready microservices quickly.

✅ Benefits of Microservices with Spring Boot:
##############################################
1. Loose coupling between services
2. Independent deployment and scaling
3. Technology flexibility (each microservice can use different tech)
4. Easier debugging, testing, and maintenance
5. Faster time-to-market


🔧 What is a Service Registry in Microservices?
#####################################################
A Service Registry is a central directory where all microservices register themselves so they can be discovered and communicate with each other without hardcoding IPs or URLs.

In Spring Boot, the most common Service Registry is Eureka Server (from Netflix OSS).

Note: 
-> Eureka server runs by default on port 8761.
-> If you run eureka server on port 8761. Client (micro services will automatically register with eureka server)

Steps to Create Eureka Server / Service Registry
###################################################

==============================================================
| Step | Description                                          |
==============================================================
| 1    | Create Eureka Server (Service Registry)              |
--------------------------------------------------------------
|      | a) Create a Spring Boot project                      |
|      | b) Add dependency: 'spring-cloud-starter-netflix-eureka-server' | Dev tool Dependencies
|      | c) Add @EnableEurekaServer in main class             |
|      | d) Configure application.properties:                 |
|         server.port=8761                                    |
|         eureka.client.register-with-eureka=false            |
|         eureka.client.fetch-registry=false  
Note: 
This tells the Eureka Server:
Don't register itself as a client.
Don't try to fetch registry data (because it's the registry).                |
--------------------------------------------------------------
| 2    | Run Eureka Server                                    |
|      | Access: http://localhost:8761                        |
==============================================================



#############################################
🛠️ What is Spring Boot Admin Server?
#############################################

Spring Boot Admin Server is a web-based UI dashboard that lets you monitor and manage Spring Boot applications in real time. It provides insight into application health, metrics, environment, thread dumps, and more — all using Spring Boot Actuator endpoints under the hood.

✅ Key Features of Spring Boot Admin:
-> Real-time health status of services
-> Memory, thread, and CPU usage
-> View logs, environment properties, and actuator endpoints
-> Email/Slack notifications for service status
-> UI-based access to /actuator endpoints

📋 How It Works:
-------------------
a. You create a Spring Boot Admin Server (dashboard).
b. Other Spring Boot apps register as Admin Clients.
c. The Admin Server shows their status and health metrics.

📌 Summary:
Use Actuator to make your app observable.
Use Admin Server to see all your services and their health in one place.

#############################
📜 Step-by-Step Instructions:
#############################
1. Set Up Spring Boot Admin Server:
-> Create a Spring Boot Project for the Admin Server.

-> You can use Spring Initializr or your IDE to create a new Spring Boot project.

-> Add Dependencies to your pom.xml:

<dependency>
  <groupId>de.codecentric</groupId>
  <artifactId>spring-boot-admin-starter-server</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

----------------------------------------------------------------------------------
Enable Admin Server by adding @EnableAdminServer annotation to your main class:

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AdminServerApplication.class, args);
  }
}
--------------------------------------------------------------------------------
Configure application.properties for Admin Server:

server.port=8080  # Admin Server running on port 8080
spring.boot.admin.ui.title=Spring Boot Admin Server  # Custom title

------------------------------------------------------------------------------

##############################
📡 What is Zipkin Server?
###############################
Zipkin Server is a distributed tracing system that helps you trace and visualize the flow of requests across multiple microservices. It shows you how long each service call takes and helps diagnose latency issues or failures in a microservices architecture.

✅ Key Features of Zipkin:
----------------------------
-> Tracks request paths across microservices (traceId)
-> Shows latency and response time of each service
-> Helps identify bottlenecks or failures
-> Integrates with Spring Cloud Sleuth for automatic tracing

Step to Install Zipikin Server:

1. Download Zipkin Servers: https://zipkin.io/pages/quickstart
2. To run jar file : java -jar file-name
3. Access that on port 9411



#############################################
First Microservices App
############################################

1. Create Spring Boot With following dependency

==================================================================================================================
| Dependency Name               | Maven Artifact ID                      | Purpose                             |
==================================================================================================================
| Eureka Discovery Client       | spring-cloud-starter-netflix-eureka-client | Registers service in Eureka     |
| Spring Boot Admin Client     | spring-boot-admin-starter-client       | Sends monitoring data to Admin UI   |
| Spring Web                   | spring-boot-starter-web                | Enables REST API support             |
| DevTools                     | spring-boot-devtools                   | Enables auto-restart and live reload|
| Spring Actuator              | spring-boot-starter-actuator           | Exposes health/metrics endpoints     |
| Zipkin Tracing               | spring-cloud-starter-zipkin            | Sends tracing data to Zipkin         |
| Sleuth (auto tracing)        | spring-cloud-starter-sleuth            | Generates trace & span IDs           |
==================================================================================================================

Step 2: Annotate the Main Class with @EnableDiscoveryClient

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyServiceApplication.class, args);
    }
}


Step 3: Configure application.properties file

# Basic Info
spring.application.name=my-service
server.port=8081

# Eureka - Optional if you are running eureka on port 8671
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Admin Server
spring.boot.admin.client.url=http://localhost:8080
management.endpoints.web.exposure.include=*

# Zipkin - Optional to mention. It will register with ZIPKIN Automatically
spring.zipkin.base-url=http://localhost:9411
spring.sleuth.sampler.probability=1.0

Very Important Note:
##########################
✅ Tools That Support Service Name Access via Eureka:

Tool	      Can Use Service Name (e.g., MICROSERVICES-1)	       Requires Eureka + Config
Feign Client	         ✅ Yes	                                        Built-in support (just use @FeignClient)
RestTemplate	         ✅ Yes	                                        Requires @LoadBalanced on bean
WebClient	         ✅ Yes	                                        Requires @LoadBalanced on WebClient builder
Browser / Postman	 ❌ No	                                        Needs actual host URL like host.docker.internal

So how to access application with Service name then? To do so, we have to use client like Feign, Webclient, RestTemplate.
Seond These Clients provide load balancer concept. Our microservices will continue to run even when url changes as microservice are being accessed using microservice names from eureka server.


Difference Between Rest Template, Webclient & Feign client
##############################################################


Feature                         | RestTemplate (with setup)  | WebClient (with setup)  | Feign Client (automatic)
------------------------------- | -------------------------- | ----------------------- | --------------------------
Can use microservice name       | Yes                        | Yes                     | Yes
Requires manual configuration   | Yes (@LoadBalanced)        | Yes (@LoadBalanced)     | No
Built-in load balancing         | Yes                        | Yes                     | Yes
URL hardcoded by default        | Yes                        | Yes                     | No
Effort level                    | Medium                     | Medium                  | Low


Example:
Tool            | Call Example                                                                     
--------------- | --------------------------------------------------------------------------------
RestTemplate    | restTemplate.getForObject("http://MICROSERVICES-1/api/data", String.class);   
  
WebClient       | webClient.get().uri("http://MICROSERVICES-1/api/data").retrieve().bodyToMono(); 

Feign Client    | @FeignClient(name = "MICROSERVICES-1") 
                  public interface Client { 
                  @GetMapping("/api/data") String getData(); 
                  }

####################################################################################
Create Micro service 2 - Use Inter microservices communication --> Use Feign Client
####################################################################################

Step 1: Create Micro Service 2 Spring boot project
Step 2: Add Following Dependencies

==================================================================================================================
| Dependency Name               | Maven Artifact ID                      | Purpose                             |
==================================================================================================================
| Eureka Discovery Client       | spring-cloud-starter-netflix-eureka-client | Registers service in Eureka     |
| Spring Boot Admin Client     | spring-boot-admin-starter-client       | Sends monitoring data to Admin UI   |
| Spring Web                   | spring-boot-starter-web                | Enables REST API support             |
| DevTools                     | spring-boot-devtools                   | Enables auto-restart and live reload|
| Spring Actuator              | spring-boot-starter-actuator           | Exposes health/metrics endpoints     |
| Zipkin Tracing               | spring-cloud-starter-zipkin            | Sends tracing data to Zipkin         |
| Sleuth (auto tracing)        | spring-cloud-starter-sleuth            | Generates trace & span IDs           |
| OpenFeign(Client)            | org.springframework.cloud              | Performs Communication with other microservice|
==================================================================================================================

Step 3: Create Fiegn Interface


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MICROSERVICES-1") 
public interface Client {
	
	@GetMapping("/message")
	public String getData();

}

Step 4: (Optional if you get an error creating Client bean, add the following to Main class )

@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservices3")
public class Microservices2Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservices3Application.class, args);
	}

}

Step 5:

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {
	
	@Autowired
	private Client client;
	
	@GetMapping("/fromsecondcontroller")
	public String getMessageFromMicroservices1() {
		return client.getData();
	}

}
Step 6: Add the following to yaml file:
spring:
  application:
    name: microservices-3
  boot:
    admin:
      client:
        url: http://localhost:8080

server:
  port: 8085

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

management:
  endpoints:
    web:
      exposure:
        include: '*'
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans

Step 7: Test using this url: http://localhost:8085/fromsecondcontroller
#########################################################################################################################



#################################################
Load Balancer Demonstration in microservices
#################################################

In microservice 1 remove port number and start the application on different port using Spring--> run configurations--->arguments---> -Dserver.port=8082 etc

# Basic Info
spring.application.name=my-service
#server.port=8081-------> remove this

# Eureka - Optional if you are running eureka on port 8671
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Admin Server
spring.boot.admin.client.url=http://localhost:8080
management.endpoints.web.exposure.include=*

# Zipkin - Optional to mention. It will register with ZIPKIN Automatically
spring.zipkin.base-url=http://localhost:9411
spring.sleuth.sampler.probability=1.0


In Spring Cloud, Feign Client integrates with Ribbon to provide client-side load balancing. Here’s an explanation of how Feign and Ribbon work together. This is auto configured. No extra configuration is required


########################
Why API gateway?
##################
1. Simplified Client Communication: Clients (mobile apps, web apps) only need to interact with one API endpoint (the API Gateway) rather than multiple microservices.

2. Decoupling: It decouples the client from the internal workings of the microservices. The client doesn't need to know how the services are structured or how they communicate internally.

3. Centralized Management: It centralizes concerns like security, authentication, and logging

How to implement API gateway:
----------------------------

Step 1 create APi gateway Spring boot project with following dependencies
-------------------------------------------------------------------------

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
Step 2: Mention routing in yaml file
--------------------------------------------
server:
  port: 5555
  
spring:
  application:
    name: API-Gateway

  cloud:
    gateway:
      routes:
        - id: microservice-api-1
          uri: lb://MICROSERVICES-1
          predicates:
            - Path=/micro1/**
          filters:
            - RewritePath=/micro1/(?<segment>.*), /${segment}
        
        - id: microservice-api-3
          uri: lb://MICROSERVICES-3
          predicates:
            - Path=/micro3/**
          filters:
            - RewritePath=/micro3/(?<segment>.*), /${segment}

Step 3: Register with eureka server
-----------------------------------
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

Step 4: Perform testing:
http://localhost:5555/micro3/fromsecondcontroller

################################################################################################

################################################
✅ What is a Spring Cloud Config Server?
################################################

Spring Cloud Config Server is a central configuration management server that allows you to store, manage, and serve external configurations for all your microservices from a single location — typically from a Git repository or file system.

🔧 Why is it needed?
------------------------------------
-> In a microservices architecture:
-> Each service may have different configuration (ports, DB URLs, API keys).
-> You may want to change config without redeploying the service.
-> Managing configs across dozens of services becomes a nightmare.

💡 Key Features:
----------------------------------------
Feature				Description
Centralized config	All services fetch config from one place

###########################################
How to secure microservice prject?
##############################################
Step 1: Create AuthService with Spring Security & JWT Token
Step 2: When you login it should generate JWT Token
Step 3: Verify the Token in API Gateway using JwtAuthenticationFilter implements GlobalFilter class
Step 4:  Inside Filter method verify the token



##########################################################################################################################################################################################################################################################################################################################################
##########################################################################################################################################################################################################################################################################################################################################
#####################################################################################################################################################################



#############################################
🔐 Core Features of Spring Security:
##############################################
1. Authentication: Verifies who you are (e.g., username & password, JWT etc.).

2. Authorization: Determines what you are allowed to do (e.g., access control for URLs, methods, etc.).

3. Protection Against Common Security Threats

a. CSRF (Cross-Site Request Forgery)
--------------------------------------
1. 🛡 CSRF (Cross-Site Request Forgery)
What it is:
An attacker tricks a logged-in user into unknowingly sending a request (like transferring money or changing a password) to your application.

Example:

-> You're logged into yourbank.com.
-> An attacker sends you a malicious link:
-> <img src="https://yourbank.com/transfer?to=attacker&amount=1000" />
-> If your session is still active, your browser might execute this without your intent.
-> Spring Security Protection:
-> Automatically adds a CSRF token to every form and expects it in requests. 
-> In a web application that uses Spring Security, the CSRF token is automatically added as a hidden input field inside HTML forms.
-> Blocks requests without valid tokens.



2. 🔒 Session Fixation
------------------------------------
What it is:
An attacker sets a known session ID for a user, then waits for the user to log in. If successful, the attacker reuses the same session.

Example:
-> Attacker sends a link with a fixed session ID:
-> https://example.com/login;jsessionid=abc123
-> User logs in; attacker now hijacks the session with abc123.
-> Spring Security Protection:
-> Regenerates the session ID after successful login by default (prevents reuse).

To Enable we do this: http.sessionManagement().sessionFixation().migrateSession();  // default behavior


3. Clickjacking

-> Here's a simplified explanation of the Clickjacking attack:
-> Attacker's Page: The attacker creates a webpage that hides a legitimate banking website inside an invisible iframe (like a hidden box).
-> User's Interaction: The user thinks they are clicking on a button (e.g., "Play Video" or "Download File") on the attacker’s page.
-> What Happens: In reality, the user is actually clicking on the invisible iframe that contains the banking page. They might unintentionally trigger actions on the banking site, like transferring money or changing their account settings.

4. Brute-Force Attack Example
-> What It Is: A Brute-Force Attack is when an attacker attempts to guess the correct credentials (like a password) by trying many possible combinations until they find the correct one.

Example:
-> The Attacker's Goal: The attacker wants to gain unauthorized access to a user's account, for example, the user’s bank account.

-> How It Works:
-> The attacker knows the username (e.g., "victim123") but not the password.
-> Using automated tools, the attacker repeatedly tries different passwords for the account, for example:
-> Password attempt 1: password123
-> Password attempt 2: 123456
-> Password attempt 3: victim123
-> Password attempt 4: qwerty

And so on, until the correct password is found.

-> How It Looks to the User: The attacker might perform hundreds of thousands or even millions of attempts per second, which can eventually crack weak passwords.

-> Attack's Success: Once the attacker guesses the correct password, they can log into the victim’s account and gain access to sensitive information, such as personal data or financial records.

-> How to Prevent Brute-Force Attacks:

a. Rate Limiting:
Limit the number of failed login attempts within a certain time frame (e.g., 5 attempts per minute). After exceeding this limit, block the IP address or enforce a longer delay before the next attempt.

b. CAPTCHA:
Use CAPTCHA challenges after a certain number of failed login attempts. This helps to ensure that it's a human attempting the login rather than a bot performing automated attacks.

c. Account Lockout:
Temporarily lock an account after a set number of failed login attempts. For example, lock the account for 10 minutes after 5 failed login attempts.

d. Multi-Factor Authentication (MFA):
Use MFA to add an extra layer of security. Even if the attacker manages to guess the password, they will still need access to the second factor (e.g., a phone or hardware token).

e. Strong Passwords:
Encourage users to use strong, unique passwords (e.g., a combination of uppercase, lowercase letters, numbers, and special characters). Use a password strength checker and enforce password complexity rules.

5. Password Handling: Secure password storage using hashing (e.g., BCrypt).

6. Integration with Spring Boot: Auto-configuration with sensible defaults.

7. JWT (JSON Web Token)



Example 1:
---------------

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {
	
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/hi")
	public String hi() {
		return "hi";
	}

}

SecuirtyConfigFile - to Permit All request
------------------------------------------

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
				 req->{
					 req.anyRequest().permitAll();
				 }
				);
		return http.build();
		
	}
}


Example 2:
-----------

Example 1:
---------------

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {
	
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/hi")
	public String hi() {
		return "hi";
	}

}

SecuirtyConfigFile - to Permit All request
------------------------------------------

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
				 req->{
					req.requestMatchers("/api/v1/welcome/hello").permitAll()
					.anyRequest().authenticated();
				 }
				);
		return http.build();
		
	}

}

Example 3: Create User Registration Implementation
-----------------------------------------------------
Step 1: Create Entity Class with Name User.java
-------------------------------------------------
package com.authservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}

---------------------------------------------------------------------------------------------------------------
Step 2: Create Repository - UserRepository.java
----------------------------------------------------------------------------------------------------------------
import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	User findByUsername(String username);
	User findByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}

------------------------------------------------------------------------------------------------------------------
Step 3: Create UserDto.java
------------------------------------------------------------------------------------------------------------------
public class UserDto {
	
	private long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
-------------------------------------------------------------------------------------------------------------------------
Step 4: Create APIResponse class to send common response for API response, which will make front end integration common
-------------------------------------------------------------------------------------------------------------------------
public class APIResponse<T> {
	
	private String message;
	private int status;
	private T data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
	

}
--------------------------------------------------------------------------------------------
Step 5 - Create AuthService - Implement Logic for creating user
---------------------------------------------------------------------------------------------
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.dto.APIResponse;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;


@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public APIResponse<String> register(UserDto dto) {
		
		if(userRepository.existsByUsername(dto.getUsername())) {
			APIResponse<String> response = new APIResponse<>();
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User with username exists");
			return response;
		}
		if(userRepository.existsByEmail(dto.getEmail())) {
			APIResponse<String> response = new APIResponse<>();
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User with Email Id exists");
			return response;
		}
		
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		userRepository.save(user);
		
		APIResponse<String> response = new APIResponse<>();
		response.setMessage("Registration Done");
		response.setStatus(201);
		response.setData("User is registered");
		
		return response;
		
		
	}

}
-------------------------------------------------------------------------------------------------
Step 6: Disable csrf in config file to access api end point from different client like postman/swagger
-----------------------------------------------------------------------------------------------------
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())  // Disable CSRF
	        .authorizeHttpRequests(auth -> 
	            auth.requestMatchers(
	            		"/api/v1/auth/register/", 
	            		"/v3/api-docs/**",
	                    "/swagger-ui/**",
	                    "/swagger-ui.html",
	                    "/swagger-resources/**",
	                    "/webjars/**").permitAll()
	            .anyRequest().authenticated()
	        );

	    return http.build();
	}

    return http.build();
}

}
------------------------------------------------------------------------------------------------------
Step 7: Add Swagger dependency for generating API documentation
------------------------------------------------------------------------------------------------------
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>

To Access Swagger - Run the Project

http://localhost:8080/swagger-ui.html

Or (sometimes):

http://localhost:8080/swagger-ui/index.html
-----------------------------------------------------------------------------------------------------------------------------

###############################################
Implementing Login Module
################################################

LoginDto.java
------------------
public class LoginDto {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
------------------------------------------------------------

AuthController.java
-----------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UpdatePasswordDto;
import com.authservice.dto.UserDto;
import com.authservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	 @PostMapping("/register")
	    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto dto) {
	        APIResponse<String> response = authService.register(dto);
	        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	    }
	 

 
	 
	 @PostMapping("/login")
	 public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto){
		 
		 APIResponse<String> response = new APIResponse<>();
		 
		 UsernamePasswordAuthenticationToken token = 
				 new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		 
		try {
			 Authentication authenticate = authManager.authenticate(token);
			 
			 if(authenticate.isAuthenticated()) {
				 response.setMessage("Login Sucessful");
				 response.setStatus(200);
				 response.setData("User has logged");
				 return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 response.setMessage("Failed");
		 response.setStatus(401);
		 response.setData("Un-Authorized Access");
		 return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	 }

}

--------------------------------------------------------------

CustomerUserDetailsService.java
-----------------------------------------
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authservice.entity.User;
import com.authservice.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),Collections.emptyList());
	}

}

--------------------------------------------------------------------------------

AppSecurityConfig.java
-----------------------
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.authservice.service.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

    String[] publicEndpoints = {
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/update-password",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
    @Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customerUserDetailsService);
		authProvider.setPasswordEncoder(getEncoder());

		return authProvider;
	}

    @Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests( req -> {
			req.requestMatchers("/api/v1/auth/register", "/api/v1/auth/login")
			   .permitAll()
			   .anyRequest()
			   .authenticated();			
		});
		
		return http.csrf().disable().build();
	}


}
-----------------------------------------------------------------------------


#################################################################################
How to perform Authorization - Role Based Access in your project
#################################################################################

Modify UserDto.java
--------------------

package com.authservice.dto;


public class UserDto {
	
	private long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String role; //This one is added

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}

Modify User.java
----------------
package com.authservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;//This is added

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	

}

Modify AppSecurityConfig.java
---------------------------------
package com.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.authservice.service.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

    String[] publicEndpoints = {
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/update-password",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
    @Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customerUserDetailsService);
		authProvider.setPasswordEncoder(getEncoder());

		return authProvider;
	}

    @Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests( req -> {
			req.requestMatchers(publicEndpoints)
			   .permitAll()
			   .requestMatchers("/api/v1/admin/welcome").hasRole("ADMIN")
			   .anyRequest()
			   .authenticated();			
		}).httpBasic();
		
		return http.csrf().disable().build();
	}


}

Modify CustomerUserDetailsService.java
---------------------------------------
package com.authservice.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authservice.entity.User;
import com.authservice.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority(user.getRole())));//This should be modified
	}

}

---------------------------

✅ 3. Test Using Postman or curl
🔹 Test with Postman
Choose GET and enter:
http://localhost:8080/api/v1/admin/welcome

Go to Authorization tab
Type: Basic Auth
Username: admin
Password: admin (assuming that's the encoded password in DB)

Click Send.

You should get a 200 OK and the "Welcome, Admin!" message if the role is correctly configured.
----------------------------------------------------------------------------------------------------------------------

Imnport Notes
############################
Stateless: User details are not stored at the server side. Hence every every request we have to perform authention

Statefull: User details are  stored at the server side. Once the use perform login, Server will generate SessionId, One Copy of session will be kept at server side & another copy is given to the client, so that for subsequent request we can authenticate the user by sending seesionId with the request to server. When session Id matches you will get the respsone

---------------------------------------------------------------------------------

Advantages of JWT Token
#########################
-> Stateless Communication
-> This performs Authentication of subsequent request made post login was successful
-> Highly Secured
-> Set Expiry time for token
-> Securing All Microservice can be made easy with JWT Token

Architecture of JWT Token
###############################
Header.PayLoad.Signature

Explanation of The Architecture

What is header?
Answer: ALGORITHM & TOKEN TYPE

Example:
{
  "alg": "HS256",
  "typ": "JWT"
}

What is PayLoad?
Answer:
-> For which user token is generated (User Details)
-> Who has generated this token (Issuer)
-> Expiry Time Of Token?
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}

What is Signature?
Answer: Secret key 

####################################################
Implementing JWT Token
####################################################

Download the dependency
------------------------
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.4.0</version>
</dependency>


Create JWTService class
---------------------------
package com.authservice.jwt;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-super-secret-key";
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public String generateToken(String username, String role) {
        return JWT.create()
            .withSubject(username)
            .withClaim("role", role)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String validateTokenAndRetrieveSubject(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
            .build()
            .verify(token)
            .getSubject();
    }
}

Modify AuthController  Class
--------------------------------

@Autowired
private JwtService jwtService;

@PostMapping("/login")
public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto) {
    APIResponse<String> response = new APIResponse<>();

    UsernamePasswordAuthenticationToken token = 
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

    try {
        Authentication authenticate = authManager.authenticate(token);
        if (authenticate.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(loginDto.getUsername(),
                authenticate.getAuthorities().iterator().next().getAuthority());

            response.setMessage("Login Successful");
            response.setStatus(200);
            response.setData(jwtToken);  // return JWT
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    response.setMessage("Failed");
    response.setStatus(401);
    response.setData("Unauthorized");
    return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
}

Develop JWTFilter Class
-----------------------------

package com.authservice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authservice.service.CustomerUserDetailsService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtService.validateTokenAndRetrieveSubject(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

Modify Secuirty Config
---------------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authservice.service.CustomerUserDetailsService;
import com.authservice.service.JwtFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtFilter filter;

    String[] publicEndpoints = {
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/update-password",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
    @Bean
	public AuthenticationProvider authProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customerUserDetailsService);
		authProvider.setPasswordEncoder(getEncoder());

		return authProvider;
	}

    @Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests( req -> {
			req.requestMatchers(publicEndpoints)
			   .permitAll()
			   .requestMatchers("/api/v1/admin/welcome").hasRole("ADMIN")
			   .anyRequest()
			   .authenticated();			
		}) .authenticationProvider(authProvider())
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		return http.csrf().disable().build();
	}


}














