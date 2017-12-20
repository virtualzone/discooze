FROM node:8-alpine as frontend_builder
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY frontend/ /usr/src/app/
RUN npm install && \
    npm run build

FROM openjdk:8-jdk-alpine
RUN mkdir -p /opt/discooze/static
WORKDIR /opt/discooze
COPY backend/target/*.jar /opt/discooze/
COPY --from=frontend_builder /usr/src/app/dist/ /opt/discooze/static/
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/discooze/backend-1.0-SNAPSHOT.jar"]