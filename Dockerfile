FROM openjdk:17

ARG JAR_FILE=/maeumgagym-infrastructure/build/libs/*.jar
COPY ${JAR_FILE} application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]
