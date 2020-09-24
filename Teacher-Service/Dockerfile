FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/TeacherService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/TeacherService.jar"]

EXPOSE 9200