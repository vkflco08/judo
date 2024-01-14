FROM amazoncorretto:17-alpine-jdk
MAINTAINER vkflco08 <vkflco8080@gmail.com>

EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} deploy-app.jar
CMD java -jar ./deploy-app.jar