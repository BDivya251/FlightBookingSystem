# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar file to container
COPY target/*.jar app.jar

# Expose port (Render auto assigns a port)
EXPOSE 8080

# Use Render's PORT variable
ENV PORT 8080

# Start the application
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
