FROM openjdk:17-jdk-slim
ARG JAR_FILE=./build/libs/backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} coffee.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-Duser.timezone=Asia/Seoul","-javaagent:/pinpoint-agent/pinpoint-bootstrap-2.5.1.jar", "-Dpinpoint.agentId=cf", "-Dpinpoint.agentName=webling-cf-prd","-Dpinpoint.applicationName=webling-cf-prd", "-jar","/coffee.jar"]