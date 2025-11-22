# ⭐️ STAGE 1: Build the application using the official Maven image ⭐️
# This image contains Java and Maven, eliminating the network failure error.
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to resolve dependencies efficiently
COPY pom.xml .

# Copy the source code
COPY src src

# Run the Maven build command directly (no need for ./mvnw)
RUN mvn clean install -DskipTests

# -------------------------------------------------------------
# ⭐️ STAGE 2: Create the smaller runtime image ⭐️
# Uses a smaller JRE image for production deployment.
# -------------------------------------------------------------
FROM eclipse-temurin:17-jre-jammy

# Set the working directory for the runtime
WORKDIR /app

# Expose the port Spring Boot will run on (8080 by default)
EXPOSE 8080

# Copy the final JAR from the build stage (Maven puts it in target/)
COPY --from=build /app/target/*.jar app.jar

# Define the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]