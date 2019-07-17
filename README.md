# Planet API

Rest API for managing planets. Technical Challenge - Dev. Back-End

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [PostgreSQL](https://www.postgresql.org/) - Production Database
* [Redis](https://redis.io/) - Production Cache
* [H2](https://www.h2database.com) - Development and test Database
* [Apache HttpComponents](https://hc.apache.org/) - Http Client
* [SpringFox](https://springfox.github.io/springfox/) - Automated JSON API documentation for API's built with Spring



## Runing in development

You can open and start this Maven project in you preffered Java IDE or just execute command:

```
mvn spring-boot:run
```

## Packaging

First you need to create application docker image by executing:
```
mvn clean package
```
After create application docker image, start containers:
```
docker-compose up -d
```
This will run 3 services containes
* planet-api - our main application
* redis - application cache
* postgres - application database

## Testing

After running application in http://localhost:8080 you access Swagger documentation page to test endpoints or use curl

### Create a new Planet
```
curl -X POST \
  http://localhost:8080/planets/ \
  -H 'Content-Type: application/json' \
  -d '{
	"name": "Alderaan",
	"diameter": "12500",
	"climate": "temperate",
	"terrain": "grasslands, mountains"
}'
```

### Get All Planets
```
curl -X GET \
  'http://localhost:8080/planets/' \
  -H 'Accept: application/json'
```

### Get All Planets by id
```
curl -X GET \
  'http://localhost:8080/planets/1' \
  -H 'Accept: application/json'
```

### Get All Planets filtering by name
```
curl -X GET \
  'http://localhost:8080/planets/?name=Alderaan' \
  -H 'Accept: application/json'
```

### Delete a planet
```
curl -X DELETE 'http://localhost:8080/planets/1 \'
```