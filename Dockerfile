FROM openjdk:17
COPY "./target/quiz-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8127
ENTRYPOINT [ "java", "-jar", "app.jar" ]