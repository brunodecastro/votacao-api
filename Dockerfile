FROM maven:3.2-jdk-8
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN mvn clean install -DskipTests -Pdocker
CMD ["java", "-jar", "target/votacao-api-docker.jar"]
EXPOSE 8080