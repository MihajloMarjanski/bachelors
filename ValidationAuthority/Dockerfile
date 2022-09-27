FROM openjdk:11
COPY target/*.jar app.jar
EXPOSE 8070
ENTRYPOINT [ "java","-jar","app.jar" ]

#FROM openjdk:11 as buildstage
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#RUN ./mvnw package
#COPY target/*.jar app.jar
#
#FROM openjdk:11
#COPY --from=buildstage /app/app.jar .
#EXPOSE 8070
#ENTRYPOINT [ "java","-jar","app.jar" ]