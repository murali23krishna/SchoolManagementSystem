FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/AuthorizationService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/AuthorizationService.jar"]

EXPOSE 9191