FROM openjdk:17

ADD target/cartdb.jar  cartdb.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "cartdb.jar" ]
