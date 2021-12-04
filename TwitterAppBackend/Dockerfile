FROM maven:3.8.3-openjdk-16-slim as build
RUN mkdir -p /app
RUN mkdir -p /app/src
COPY src /app/src
COPY pom.xml /app
RUN mvn clean package -Dmaven.test.skip -f /app/pom.xml

FROM openjdk:16-jdk-slim
RUN mkdir -p /app
COPY --from=build /app/target/TwitterAppBackend-0.0.1.jar /app/TwitterAppBackend.jar
EXPOSE 8080
ARG dbAdressArg
ENV dbAdress=$dbAdressArg
ARG dbPortArg
ENV dbPort=$dbPortArg
ARG loginToDbArg
ENV loginToDb=$loginToDbArg
ARG passwordToDbArg
ENV passwordToDb=$passwordToDbArg
ARG databaseNameArg
ENV databaseName=$databaseNameArg
ARG baererKeyArg
ENV baererKey=$baererKeyArg
ENTRYPOINT ["java","-jar","/app/TwitterAppBackend.jar"]
