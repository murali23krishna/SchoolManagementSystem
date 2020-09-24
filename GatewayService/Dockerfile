FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/GatewayService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/GatewayService.jar"]

EXPOSE 8085