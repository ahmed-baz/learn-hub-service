FROM openjdk:17-alpine
LABEL maintainer="developer.baz@gmail.com"
WORKDIR /usr/local/bin/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} learn-hub-service.jar
EXPOSE 2222
ENTRYPOINT ["java","-jar","learn-hub-service.jar"]
