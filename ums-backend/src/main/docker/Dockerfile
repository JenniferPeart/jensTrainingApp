FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080

# This file describes how Docker should run our Spring Boot application. 
# It uses Java 11 from AdoptOpenJDK and copies the application JAR file to application.jar. 
# It then runs that JAR file to start our Spring Boot application.