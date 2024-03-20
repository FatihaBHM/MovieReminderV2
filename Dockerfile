FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY . .

RUN exposition/mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG VERSION=1.0-SNAPSHOT
COPY --from=build /workspace/app/exposition/target/exposition-$VERSION.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
