FROM gcr.io/google_appengine/openjdk8
VOLUME /tmp
ADD audiobucket-1.0-jar-with-dependencies.jar app.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar" ]