FROM amazoncorretto:17-alpine-jdk
COPY build/libs/app.jar /app.jar

# Expose server port.
ARG host_port=8080
EXPOSE host_port

ENTRYPOINT ["java", "-jar", "/app.jar"]