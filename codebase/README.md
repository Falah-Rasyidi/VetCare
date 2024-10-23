# Petcare Project Instructions

## Pre-Req
- `docker-compose-vars.env` file needs to be made in root of `/codebase`
  - contains:
    - `DB_PASSWORD`=yourpassword (replace with any password you want to use)
    - `spring.h2.console.enabled=false`

## Run Instructions
If you're doing normal local dev, use dev run. 
When you're happy to submit, run it in docker and ensure you use the second command to rebuild the images properly.
### Dev Run
```shell
cd codebase
./mvnw clean spring-boot:run
```
- Runs Springboot frontend with H2 Database
<br/><br/>
- Page: http://localhost:8080
- DB: http://localhost:8080/h2-console
  - username: `sa`
  - password: `password`

### Docker Run
```shell
cd codebase
docker-compose -f docker-compose-mysql.yml build    
docker-compose -f docker-compose-mysql.yml --env-file docker-compose-vars.env up
```
#### Note: WILL NOT BUILD IF THERE ARE FAILING TESTS 
- Docker container with:
  - Springboot Frontend
  - MySQL Container
  - Adminer Container
<br/><br/>
- Page: http://localhost:8080
- DB Adminer: http://localhost:9000
  - username: `root`
  - password: `whatever is in your env file`
  - database: `petcare`






