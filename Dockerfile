FROM openjdk:17-jdk-slim

LABEL maintainer = "Boubacar Siddy DIALLO boubasiddy00@gmail.com"

ADD docker/admin-management-0.0.1.jar admin-management.jar

ENTRYPOINT ["java", "-jar", "admin-management.jar"]