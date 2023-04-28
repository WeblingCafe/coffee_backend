FROM openjdk:17-jdk-slim
ARG JAR_FILE=./build/libs/backend-0.0.1-SNAPSHOT.jar
ARG PINPOINT_VERSION=2.5.1

WORKDIR /usr/local

RUN apt-get update && \
    apt-get install -y apt-utils && \
    apt-get upgrade -y && \
    apt-get install -y wget && \
    wget https://github.com/naver/pinpoint/releases/download/v${PINPOINT_VERSION}/pinpoint-agent-${PINPOINT_VERSION}.tar.gz && \
    tar xzf pinpoint-agent-${PINPOINT_VERSION}.tar.gz && \
    rm pinpoint-agent-${PINPOINT_VERSION}.tar.gz

COPY ${JAR_FILE} coffee.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-Duser.timezone=Asia/Seoul", "-javaagent:/usr/local/pinpoint-agent-2.5.1/pinpoint-bootstrap-2.5.1.jar", "-Dpinpoint.agentId=cf", "-Dpinpoint.applicationName=webling-ip-pinpoint-prd" , "-jar", "/usr/local/coffee.jar"]