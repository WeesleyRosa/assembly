FROM gradle:6.8-jdk11
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
ENTRYPOINT [“java”,”-Dspring.data.mongodb.uri=mongodb://mongo:27017/assembly”, “-Djava.security.egd=file:/dev/./urandom”,”-jar”,”/assembly.jar”]
