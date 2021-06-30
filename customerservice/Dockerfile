FROM openjdk:8-jdk-alpine
COPY "./target/customerservice-0.0.1-SNAPSHOT.jar" "appcustomerservice.jar"
EXPOSE 8082
ENTRYPOINT ["java","-jar","appcustomerservice.jar"]