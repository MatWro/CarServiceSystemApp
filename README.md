# Car Service System App

This is a simple Car Service System application that allows users to manage their cars and repairments.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Examples API Endpoints](#api-endpoints)
- [Testing and Feedback](#testing-and-feedback)

## Features

- Add and delete cars
- Assign cars to specific users
- Manage repairments for each car
- RESTful API for interacting with the application

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- Lombok
- H2 Database (for development)
- MySql Database(on docker)
- Docker (optional, for containerization)

## Getting Started

To run the application using Docker, follow these steps:

1. Clone the repository: `git clone https://github.com/MatWro/CarServiceSystemApp.git`
2. Navigate to the project directory: `cd CarServiceSystemApp`

Before building the Docker image, make sure you have Maven and Docker installed on your system. Then run:

3. Clean and build the Maven project: `mvn clean install`
4. Build the Docker image for the application: `docker build --tag car-service .`
5. Start the application and database containers: `docker-compose up`

## Usage

 Use the API endpoints to manage cars, users, and repairments.


## Example API Endpoints

- `GET /cars`: Get a list of all cars.
- `GET /cars/{id}`: Get details of a specific car by ID.
- `POST /cars`: Add a new car.
- `POST cars/user/{id}`: Add a new car for specific user. 
- `DELETE /cars/{id}`: Delete a car by ID.

## Testing and Feedback

Feel free to test this application! After cloning the repository and running the application according to the instructions above, you can explore various endpoints available in the API. The application is accessible at `http://localhost:8080`.

If you have any questions, comments, or suggestions regarding this application, please share them. Your feedback is highly valuable to us and will help us improve and enhance this application.

Thank you for your interest and testing! We look forward to receiving your feedback!


