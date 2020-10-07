FROM openjdk:8-jre-alpine3.7

COPY SpaceGame-0.0.1-SNAPSHOT.jar SpaceGame.jar
EXPOSE 8080
COPY wrapper.sh /wrapper.sh
COPY docker /usr/bin/docker
RUN groupadd -g 975 docker && \
   usermod -aG docker jenkins
RUN chmod 555 /wrapper.sh

ENTRYPOINT ["/wrapper.sh"]
