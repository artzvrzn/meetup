FROM adoptopenjdk/openjdk11:armv7l-ubuntu-jre11u-2022-10-07-03-16-beta-nightly

ADD ./target/meetup-api.jar /app/

CMD ["java", "-jar", "/app/meetup-api.jar"]

EXPOSE 8080 8080