FROM eclipse-temurin:17-jdk-alpine AS base

##WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN chmod +x ./mvnw
#RUN ./mvnw dependency:go-offline
#
#COPY src ./src
#
#CMD ["./mvnw", "clean", "test"]

# First build the app
FROM base AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

# Now run it
FROM base AS runner
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]