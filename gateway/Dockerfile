FROM openjdk:17-alpine
ARG JAR_FILE=target/gateway_jar/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]
