FROM java:8-jre
MAINTAINER Muralikrishna <murali23krishna@gmail.com>

ADD ./target/RegistryService.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/RegistryService.jar"]

EXPOSE 8761