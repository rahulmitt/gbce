#specify the base image -- the image from which the container should be created
FROM openjdk:8-jdk-alpine
EXPOSE 8080

#copy the jar into the root folder inside the image
ADD target/gbce-1.0-SNAPSHOT.jar /gbce-1.0-SNAPSHOT.jar

#set the command which should be run at startup
ENTRYPOINT ["sh", "-c", "java -jar /gbce-1.0-SNAPSHOT.jar"]

