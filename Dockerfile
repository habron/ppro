FROM openjdk:14
EXPOSE 8080
ADD projekt-1.war projekt-1.war
ENTRYPOINT ["java", "-jar", "projekt-1.war"]