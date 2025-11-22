# Use a standard OpenJDK image as the base for building the application
FROM eclipse-temurin:17-jdk-jammy AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper files and the pom.xml to download dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the rest of the application source code
COPY src src

# Make the wrapper script executable and run the Maven build
RUN chmod +x mvnw
# ⭐️ CHANGE THIS LINE ⭐️
RUN ./mvnw -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true clean install -DskipTests

# --- Second Stage: Create the smaller runtime image ---
FROM eclipse-temurin:17-jre-jammy

# Set the working directory for the runtime
WORKDIR /app

# Expose the port Spring Boot will run on (8080 by default)
EXPOSE 8080

# Copy the final JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Define the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]