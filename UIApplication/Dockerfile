FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/UIService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/UIService.jar"]

EXPOSE 8411