# cobspec server
Basic app that uses my [micro-http-server](https://github.com/aaizuss/micro-http-server) to pass 8thLight's [cob_spec](https://github.com/8thlight/cob_spec).
It's essentially a file server (it can serve text, html, jpeg/jpg, png, and gif files), but it also supports
* Range requests to serve partial content from a text file
* Decoding parameters
* Setting a cookie
* Basic Authentication (try `GET /logs`)
* HTTP Methods: `GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS`

### Dependencies
* [Java SE 8](http://docs.oracle.com/javase/8/docs/)
* [Maven](https://maven.apache.org/)
* [JUnit 4.12](http://junit.org/junit4/)
* [micro-server](https://github.com/aaizuss/micro-http-server)
    * for every update, you need to go to the `micro-server` root directory and run 
        * `mvn package`
        * `mvn install:install-file -Dfile=./target/micro-server-1.0-SNAPSHOT.jar -DpomFile=./pom.xml`

### Compilation
* `mvn package && mvn clean compile assembly:single` builds a jar with dependencies

### Tests
* Unit Tests: `mvn test`
* cob_spec acceptance test: [instructions]((https://github.com/8thlight/cob_spec))

### Usage
In the root directory, run `java -jar ./target/cobspec-1.0-SNAPSHOT-jar-with-dependencies.jar`

By default, the server runs on port 5000 and the public directory in this repo.
You can specify the port and/or directory with
* `-p` for the port
* `-d` for the directory. The directory must be an absolute path

You can visit `localhost:[port]` in your browser to interact with the server, or use software like [Postman](https://www.getpostman.com/apps) 

