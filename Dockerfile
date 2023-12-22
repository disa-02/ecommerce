FROM openjdk:17-jdk-alpine 
COPY ./target/ecommerce-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3000 
CMD ["java","-jar","/app.jar"]
