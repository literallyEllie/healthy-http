FROM amazoncorretto:17-alpine-jdk
COPY server/build/libs/app.jar /app.jar

# Expose server port.
ARG HEALTHY_SERVER_PORT=8080
EXPOSE HEALTHY_SERVER_PORT

ENTRYPOINT ["java", "-jar", "/app.jar"]