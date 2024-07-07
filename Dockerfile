# Use a base image containing Java runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file from the host to the container
COPY target/BookCatalogue-V-0.0.1-SNAPSHOT.jar app.jar

# Copy the data directory containing the H2 database file
COPY data /app/data

# Expose the port your application runs on
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]