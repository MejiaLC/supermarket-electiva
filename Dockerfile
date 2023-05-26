FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp
ARG JAR_FILE

COPY ${JAR_FILE} com.example.demo-1.0.0.jar
ENTRYPOINT [ "java", "-jar", "/com.example.demo-1.0.0.jar" ]