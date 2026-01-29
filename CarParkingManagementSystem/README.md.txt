# Car Parking Management System

## Project Description
This is a Spring Boot based web application for managing car parking information.
The system stores car details and displays parked car information using MVC architecture.

## Technologies Used
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- MySQL
- Thymeleaf
- Maven

## Project Structure
- Controller: Handles HTTP requests
- Service: Contains business logic
- Repository: Handles database operations
- Model: Represents database tables
- View: Displays data using HTML

## Features
- Add car parking information
- View all parked cars
- MVC architecture implementation
- Database integration using JPA

## Database Configuration
Update `application.properties` file:

spring.datasource.url=jdbc:mysql://localhost:3306/parking_db  
spring.datasource.username=root  
spring.datasource.password=1234  

## How to Run the Project
1. Open the project in IntelliJ IDEA or Eclipse
2. Make sure MySQL is running
3. Create database named `parking_db`
4. Update database username and password
5. Run `CarParkingManagementSystemApplication.java`
6. Open browser and go to:
   http://localhost:8080

## Output
The application displays car parking details in a table format.

## Author
Name: __________________  
Course: __________________  
University: __________________
