FROM openjdk:17
COPY target/wbs-client-1.0.jar /opt/apps/wbs-client-1.0.jar
WORKDIR /opt/apps/
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "wbs-client-1.0.jar"]
