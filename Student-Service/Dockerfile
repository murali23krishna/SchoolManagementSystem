FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/StudentService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/StudentService.jar"]

EXPOSE 9100