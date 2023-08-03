## Stage 1: Build the Maven project
#FROM jelastic/maven:3.8.6-openjdk-20.ea-b24 AS build
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the Maven project files to the container
#COPY pom.xml .
#
## Download the project dependencies (cache them in a separate layer)
#RUN mvn dependency:go-offline
#
## Copy the source code to the container
#COPY src ./src
#
## Build the application
#RUN mvn clean install package

# Stage 2: Create a smaller image with Java 17 only
FROM openjdk:20-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the previous stage
COPY target/CarServiceSystemApp-0.0.1-SNAPSHOT.jar .

# Command to run your Java application
CMD ["java", "-jar", "CarServiceSystemApp-0.0.1-SNAPSHOT.jar"]