FROM openjdk:17-alpine
ARG JAR_FILE=target/deal_jar/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
