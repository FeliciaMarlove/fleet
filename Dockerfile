FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD

COPY pom.xml /build/
COPY cert/DigiCertGlobalRootCA.crt /build/cert/DigiCertGlobalRootCA.crt

COPY src /build/src/

WORKDIR /build/

RUN update-ca-certificates
RUN mvn package

FROM openjdk:11

COPY --from=MAVEN_BUILD /build/target/fleet-0.0.1-spring-boot.jar fleet-0.0.1-spring-boot.jar
COPY --from=MAVEN_BUILD /build/cert/DigiCertGlobalRootCA.crt usr/local/DigiCertGlobalRootCA.crt

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/fleet-0.0.1-spring-boot.jar"]

RUN update-ca-certificates



# ### LOCAL ##############

# FROM openjdk:11

# COPY target/fleet-0.0.1-spring-boot.jar fleet-0.0.1-spring-boot.jar
# COPY cert/DigiCertGlobalRootCA.crt usr/local/DigiCertGlobalRootCA.crt

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "/fleet-0.0.1-spring-boot.jar"]

# RUN update-ca-certificates