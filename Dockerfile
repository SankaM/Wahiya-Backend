FROM adoptopenjdk/openjdk11:ubi

COPY build/libs/*.jar /libs/app.jar

EXPOSE 3005

ENTRYPOINT ["java", "-jar", "/libs/app.jar"]