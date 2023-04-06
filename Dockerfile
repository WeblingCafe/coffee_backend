FROM openjdk:17-jdk-slim
ARG JAR_FILE=./coffee_backend/build/libs/backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} coffee.jar
VOLUME ["/logs"]
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prd" ,"/coffee.jar"]