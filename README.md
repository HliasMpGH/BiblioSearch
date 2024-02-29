# Spring Boot Local Server App

## Overview

This project represents a Spring Boot server application hosted locally, designed to fulfill a specific use case as part of a larger application. The server is managed by a local server handler written in Java using Maven as the build tool.

## Requirements

- JavaFX
- Maven
- Spring Boot

## Setting Up Database Credentials

1. Navigate to the [src/main/resources](gui/src/main/resources) directory in the project.

2. Open the `[application.properties](gui/src/main/resources/application.properties)` file in a text editor.

3. Locate the database configuration section. You'll find properties like
   `spring.datasource.username`, and `spring.datasource.password`.

5. Set your desired username and password for the H2 embedded database. For example:

   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=myusername
   spring.datasource.password=mypassword

## Local Server Handler

The local server handler is a Java application responsible for managing the Spring Boot server. It handles start and stop requests, coordinates with other components of the larger application, and ensures smooth operation of the server.

The [handler](exe/Local%20Server%20Handler.exe) will communicate with the Spring Boot server and manage its functionalities.

## Use Case

The use case presented streamlines the process of finding books by allowing users to search through a collection with queries and genre specifications. Users can see detailed information, filter results, creating a efficient book discovery experience.


## License

This project is licensed under the [GPL-2.0 License](LICENSE).


# Appreciation for UI Contributors



Special thanks to following individuals for their exceptional help in crafting the UI of the use case. Their creativity and dedication have played a key role in shaping a user-friendly and visually appealing interface.

  

-  **[Marina](https://github.com/MarinaGolf12)**




-  **[Dimitris](https://github.com/dimitriospapathanasiou)**




-  **[Maria](https://github.com/mariachrisochoou)**

---
