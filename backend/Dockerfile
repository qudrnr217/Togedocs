
FROM gradle:7.4-jdk-alpine

WORKDIR /backend

COPY . .

RUN gradle clean build --no-daemon

EXPOSE 8081

CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
