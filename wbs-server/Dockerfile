FROM openjdk:17
COPY target/wbs-server-1.0.jar /opt/apps/wbs-server-1.0.jar
WORKDIR /opt/apps/
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "wbs-server-1.0.jar"]
