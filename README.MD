# 💀 BrutePath 🔒

##### NAME

NY TINA ANDRIANTSOA Fifaliana Loica

##### MAIL

hei.loica.2@gmail.com

##### STD REF

STD21044

## _Java path brute force_

👉 BrutePath is a java script for Directory Brute Forcing
It use cached thread pool to test the content of a word list

👉 CachedThreadPool, which means it creates new threads on demand and reuses existing threads if they are available. This can be effective for tasks that run quickly and can be handled by many threads
<sub><sup>(⚠️ Performance depend on your CPU)</sup></sub>

## Tech 🖥️

BrutePath uses a number of open source projects to work properly:

- [JAVA]
- [MAVEN]

## Installation 🚀

BrutePath requires [java](https://www.oracle.com/java/technologies/downloads/) v20+ to run.

Install the dependencies and run

```sh
./mvnw clean install
./mvnw package
java -jar target/BrutePath-1.0-SNAPSHOT.jar
```

For simplicity...

- linux

```sh
bash run.sh
```

- windows

```sh
./run.bat
```

## Best Perf 🏎️

======================= BRUTE PATH ========================

find : http://127.0.0.1:5000//
execution time: 241 ms

find : http://127.0.0.1:5000/image
execution time: 1960 ms

find : http://127.0.0.1:5000/index
execution time: 1972 ms
