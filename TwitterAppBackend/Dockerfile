FROM openjdk:16-jdk-slim
COPY target/TwitterAppBackend-0.0.1-SNAPSHOT.jar backend-spring.jar
ARG passwordToDbArg
ENV passwordToDb=$passwordToDbArg
ARG baererKeyArg
ENV baererKey=$baererKeyArg
ENTRYPOINT ["java","-jar","/backend-spring.jar"]
