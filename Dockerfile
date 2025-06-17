
# Stage 1: Build the Spring Boot application
FROM maven:3-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies (this layer is only rebuilt if pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Create the final lean runtime image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR from the build stage into the final image
# Make sure "GMBackend.jar" is the exact name of your final JAR file.
# You might need to adjust '/app/target/*.jar' if your JAR name is more specific
# e.g., /app/target/your-app-name-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/*.jar GMBackend.jar

# Expose the port Spring Boot listens on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "GMBackend.jar"]