FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8333
ENTRYPOINT ["java","-jar","/app.jar"]