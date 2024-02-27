# Spring Boot Local Server App

## Overview

This project represents a Spring Boot server application hosted locally, designed to fulfill a specific use case as part of a larger application. The server is managed by a local server handler written in Java using Maven as the build tool.

## Requirements

- JavaFX
- Maven
- Spring Boot

## Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/HliasMpGH/BiblioSearch.git
    ```

2. **Navigate to the project directory:**

    ```bash
    cd BiblioSearch/gui
    ```

3. **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    java -jar Local\ Server\ Handler-jar-with-dependencies.jar
    ```

   The handler will communicate with the Spring Boot server and manage its functionalities.

## Local Server Handler

The local server handler is a Java application responsible for managing the Spring Boot server. It handles start and stop requests, coordinates with other components of the larger application, and ensures smooth operation of the server.

## Use Case

The use case presented streamlines the process of finding books by allowing users to search through a collection with queries and genre specifications. Users can see detailed information, filter results, creating a efficient book discovery experience.


## License

This project is licensed under the [GPL-2.0 License](LICENSE).

---
