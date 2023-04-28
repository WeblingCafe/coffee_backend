FROM openjdk:17-jdk-slim
ARG JAR_FILE=./build/libs/backend-0.0.1-SNAPSHOT.jar

RUN wget https://repo1.maven.org/maven2/com/navercorp/pinpoint/pinpoint-agent/2.2.3/pinpoint-agent-2.2.3.tar.gz && \
    tar xvzf pinpoint-agent-2.2.3.tar.gz

COPY ${JAR_FILE} coffee.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prd", "-Duser.timezone=Asia/Seoul", "-javaagent:/pinpoint-agent/pinpoint-bootstrap-2.2.3.jar", "-Dpinpoint.agentId=cf", "-Dpinpoint.applicationName=webling-ip-pinpoint-prd" , "/coffee.jar"]