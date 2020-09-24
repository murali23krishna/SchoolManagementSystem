FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/ConfigService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ConfigService.jar"]

EXPOSE 9000