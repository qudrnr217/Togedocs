
FROM gradle:7.4-jdk-alpine

WORKDIR /jenkinstest/backend

COPY . .

RUN gradle clean build --no-daemon

EXPOSE 8010

CMD ["java", "-jar", "build/libs/Stamper-1.2.1-SNAPSHOT.jar"]
