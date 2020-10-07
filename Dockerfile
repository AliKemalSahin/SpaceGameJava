FROM openjdk:8-jre-alpine3.7

COPY SpaceGame-0.0.1-SNAPSHOT.jar SpaceGame.jar
EXPOSE 8080
COPY wrapper.sh /wrapper.sh

RUN chmod 555 /wrapper.sh

ENTRYPOINT ["/wrapper.sh"]
