FROM gradle:7.4-jdk-alpine

COPY /src/main/resources/application.yml application.yml
RUN gradle clean build --no-daemon
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "-Dspring.config.location=/application.yml,/home/ubuntu/properties/application-secret.yml,/home/ubuntu/properties/application-db.yml", "build/libs/backend-0.0.1-SNAPSHOT.jar"]

