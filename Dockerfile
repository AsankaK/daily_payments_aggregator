FROM openjdk:17
WORKDIR /
ADD target/com.asankak.daily_payments_aggregator-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/com.asankak.daily_payments_aggregator-0.0.1-SNAPSHOT.jar"]
