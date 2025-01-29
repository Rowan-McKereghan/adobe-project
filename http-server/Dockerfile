FROM maven:3.9.9-amazoncorretto-23-alpine AS build
WORKDIR /build-dir
COPY /int-to-roman/pom.xml .
COPY /int-to-roman/src ./src
RUN mvn clean package


FROM amazoncorretto:23.0.2-alpine
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
WORKDIR /int-to-roman-server
COPY --from=build /build-dir/target/int-to-roman-1.0-SNAPSHOT.jar int-to-roman.jar
EXPOSE 8080
RUN apk add curl
CMD ["java", "-jar", "/int-to-roman-server/int-to-roman.jar"]