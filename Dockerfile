FROM maven:3-openjdk-16-slim AS build
WORKDIR /app
COPY . .
RUN mvn -B package

FROM openjdk:16-slim
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080