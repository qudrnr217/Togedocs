
FROM gradle:7.4-jdk-alpine
WORKDIR /JenkinsTest/backend
COPY . .
RUN gradle clean build --no-daemon
CMD java -jar build/libs/*.jar


