# We don't want to start from scratch.
FROM openjdk:17

# Create an application directory
RUN mkdir -p /app

# The /app directory should act as the main application directory
WORKDIR /app

# Copy or project directory (locally) in the current directory of our docker image (/app)
COPY /target/*.jar ./app.jar

# Expose $PORT on container.
# We use a variable here as the port is something that can differ on the environment.
EXPOSE $PORT

# Start the app
CMD [ "java", "-jar", "./app.jar" ]