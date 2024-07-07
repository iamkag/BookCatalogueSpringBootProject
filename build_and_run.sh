#!/bin/bash

docker compose down
docker rmi -f $(docker images -f "dangling=true" -q)
mvn clean install

# Log the contents of the target directory
echo "Contents of the target directory:"
ls -l target

# Verify that the JAR file is in the target directory
if [ -f target/BookCatalogue-V-0.0.1-SNAPSHOT.jar ]; then
    echo "JAR file found, proceeding to build Docker image."
else
    echo "JAR file not found, build failed."
    exit 1
fi

# Build the Docker image
docker build -t bookcatalogue .

# Run the Docker container
docker run -p 8080:8080 bookcatalogue

docker compose up --build -d --remove-orphans
