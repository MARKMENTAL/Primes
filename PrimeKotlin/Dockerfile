FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim

WORKDIR /opt/app

COPY src/PrimeSieveKotlin.jar .

RUN apk update && apk add bash

CMD ["java", "-jar", "PrimeSieveKotlin.jar"]
