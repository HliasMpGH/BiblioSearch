
# Spring Boot Local Server Application

  

## Overview

  

This project represents a Spring Boot server application hosted locally, designed to fulfill a specific use case as part of a larger application. The server is managed by a local server handler written in Java using Maven as the build tool.

  

## Requirements
 

- JavaFX

- Maven

- Spring Boot


## Set up and Build

1. **Clone the repository:**

    ```bash
    git clone https://github.com/HliasMpGH/BiblioSearch.git
    ```


2. **Navigate to the project resources:**

    ```bash
    cd BiblioSearch/gui/src/main/resources
    ```
    



2. **Open [application.properties](gui/src/main/resources/application.properties) file in a text editor**

 

4. **Customize the configurations to your liking**

	- provide configurations for
	  `spring.datasource.username` 
	  and
	   `spring.datasource.password` 
	- The same credentials can be used later to access the data in http://localhost:8080/h2-console

	- After setting your desired username and password for the embedded database, it should look something like this:


	```
	spring.datasource.driverClassName=org.h2.Driver

	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

	spring.h2.console.enabled=true

	spring.datasource.url=jdbc:h2:mem:testdb

	spring.datasource.username=your_username

	spring.datasource.password=your_password
	```
3. **Build the project :**

    ```bash
    mvn clean install
    ```

4. **Run the application:**

    ```bash
    java -jar Local\ Server\ Handler-jar-with-dependencies.jar
    ```


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