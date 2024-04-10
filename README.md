# Fitness API
This document provides instructions on setting up and running project locally/

## Running using local postgres instance
###  1. Prerequisites
Before you begin, ensure you have the following installed on your local machine:

*   JDK 21 (Java Development Kit)
*   PostgreSQL
*   additionally if you want to run unit and integration tests you will need Docker
### 2. Setup
1. Clone the repository to your local machine:
````
https://github.com/ksawio97/fitness-api
````
2. Navigate to the project directory:
````
cd fitness-api
````
3. Configure the database connection in the application.properties file located in the src/main/resources directory.
   Make sure to adjust the database URL, username, and password according to your local PostgreSQL setup.

4. Run the project using Gradle.
5. Once the project is running, you can access the API endpoints.
## Running with Docker Compose
###  1. Prerequisites
* Docker
### 2. Setup
Use Docker Compose to run the project.
````
docker compose up
````
You can modify the '.env' file parameters in project root dir.