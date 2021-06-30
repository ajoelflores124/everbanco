FROM openjdk:8-jdk-alpine
COPY "./target/transactionservice-0.0.1-SNAPSHOT.jar" "apptransaction.jar"
EXPOSE 8082
ENTRYPOINT ["java","-jar","apptransaction.jar"]