# Use the official maven/Java parent image
FROM maven:3.8.3-openjdk-11 AS build

# Set the working directory in the image
WORKDIR /usr/src/app

# Copy local code to the container image
COPY . .

# Build the JAR file
RUN mvn clean package

# Use OpenJDK to run your JAR
FROM openjdk:11-jre-slim

# Copy the JAR file from the build stage
COPY --from=build /usr/src/app/target/ZachBot-1.0-SNAPSHOT.jar /app/ZachBot.jar

# Set the working directory
WORKDIR /app

# Command to run the application
CMD ["java", "-jar", "ZachBot.jar"]
