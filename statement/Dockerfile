FROM openjdk:17-alpine
ARG JAR_FILE=target/statement_jar/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
