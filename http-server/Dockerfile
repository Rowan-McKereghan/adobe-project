FROM amazoncorretto:23.0.2-alpine
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
WORKDIR /int-to-roman-server
COPY /int-to-roman/target/int-to-roman-1.0-SNAPSHOT.jar int-to-roman.jar
EXPOSE 8080
RUN apk add curl
CMD ["java", "-jar", "/int-to-roman-server/int-to-roman.jar"]