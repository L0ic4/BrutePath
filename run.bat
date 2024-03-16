CALL ./mvnw.cmd clean install
CALL ./mvnw.cmd package

CALL java -jar target/BrutePath-1.0-SNAPSHOT.jar