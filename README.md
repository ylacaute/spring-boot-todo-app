# WORK IN PROGRESS


## Build
 * Build application (only unit tests)
 
```mvn clean install```

 * Build application (all tests)
 
```mvn clean install -skipITs=false```

 * Build application with CI profile (no color, H2 database, all tests)
  
```mvn clean install -P ci```

## Run
* Start app with an existing Postegres database

```mvn spring-boot:run```

* Start app with an H2 in-memory database

```mvn spring-boot:run -Dspring.profiles.active=h2mem```
