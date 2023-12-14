FROM gradle:7.6-jdk17 AS build

WORKDIR /home/project
COPY ./ .
RUN gradle clean build

FROM eclipse-temurin:17-alpine

WORKDIR /app
COPY --from=build /home/project/build/libs/*.jar /app/app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]