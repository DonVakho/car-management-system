# car management system

## Project description

This is a Car Management System application backend implemented using the following stack:

* **Spring boot** - Provides the rest endpoints, authentication and business logic
* **PostgrsSQL** - Stores user data
* **GraphQl** - Provides alternative graph api alongside to REST
* **Docker** - Provides containerization and is used to run all parts of application together.

## How to run

**To run the application it is required to have docker installed.**

project contains script file called `docker-compose.yml` to run the application just run the command `docker compose up`
from root directory.

`docker-compose` contains default configuration for credentials, database name and so on. 
If needed default values can be modified. Change need to only happen in `docker-compose` file.  

In docker 3 containers will be started:

* Application backend
* PostgreSQL
* pgAdmin (for database ui)


## Additional notes
* Backend code has a code style-check integrated, so when making any changes to the code, please first verify that
style-check passes by running `gradle build`