#mvn clean package

docker cp \
  target/stream-1.0-SNAPSHOT.jar \
  pulsar-flink-stateful-streams-taskmanager-1:opt/flink/click-job.jar

docker exec -it pulsar-flink-stateful-streams-taskmanager-1 ./bin/flink run \
--class com.berdanakbulut.stream.compute.socket.ClickStream \
  click-job.jar
