FROM openjdk:11
COPY target/maas4you.jar maas4you.jar
ENTRYPOINT [ "java", "-jar", "/maas4you.jar" ]