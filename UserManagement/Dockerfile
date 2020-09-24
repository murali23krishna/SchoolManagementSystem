FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/UserService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/UserService.jar"]

EXPOSE 9211