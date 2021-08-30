FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} discovery.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/discovery.jar"]