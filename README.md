# Planet API

Rest API for managing planets. Technical Challenge - Dev. Back-End

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [PostgreSQL](https://www.postgresql.org/) - Production Database
* [H2](https://www.h2database.com) - Development and test Database
* [Apache HttpComponents](https://hc.apache.org/) - Http Client
* [SpringFox](https://springfox.github.io/springfox/) - Automated JSON API documentation for API's built with Spring



## Runing in development

You can open and start this Maven project in you preffered Java IDE or just execute command:

```
mvn spring-boot:run
```

## Packaging

First you need to create docker image executing:

```
mvn clean package
```
After create docker image:

```
docker-compose up -d
```
