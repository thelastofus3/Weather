FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM eclipse-temurin:21-jre
WORKDIR /
COPY --from=build /target/*.jar weather.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","weather.jar"]