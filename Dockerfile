FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
MAINTAINER Hugo Blanc
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn -Dmaven.test.skip  package


FROM adoptopenjdk/openjdk11:alpine-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/blogJava-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8081
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "blogJava-0.0.1-SNAPSHOT.jar"]