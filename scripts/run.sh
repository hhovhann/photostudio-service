#/bin/sh
mvn clean package -DskipTests && java -jar ./target/photostudio-service-1.0.0-SNAPSHOT.jar