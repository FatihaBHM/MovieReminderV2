# Movie Reminder

## Note: Création du projet en DDD (architecture hexagonale)

### Exposition

- controllers
    - HelloController.java

- resources
    - application.properties (config BDD | Swagger | ...)

### Domain

- entities/*Entity.java
- repositories/*Repository.java

### Infrastructure

- Repository
    - *Port.java
    - *Adapter.java

# 2ème étape

## Exposition

- controllers
    - api
        - media
            - MovieController.java

## Application

- Service
    - MovieServicePort.java
    - MovieServiceAdapter.java

### Infrastructure

- Repository
    - *Port.java
    - *Adapter.java

# 3ème etape

## Domain

- ajout de `implements UserDetails` dans UserEntity.java

## Exposition

- configuration
    - MyUserDetailsService.java
    - SecurityConfiguration.java (spring secutity)
    - JwtService.java
    - JwtFilter.java
    - `ajout du filter avec addFilterBefore(...)` dans SecurityConfiguration.java
- api
    - authentication
        - Usercontroller.java (login | register)
