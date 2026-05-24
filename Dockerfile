FROM eclipse-temurin:17-jdk-alpine
COPY "./target/quiz-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8127
ENTRYPOINT [ "java", "-jar", "app.jar" ]