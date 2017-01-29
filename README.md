# LogFileReader

With LogFileReader one can monitor log. 

LogFileReader is a simple dashboard with one simple widget. Application monitors the changes to `file.txt` and 
display distinct number of ERROR, WARNING and INFO messages for a last 5 seconds.

## Usage

1. Download the repository
2. Start LogFileReader server by running a command mvn `spring-boot:run`
3. Open your favourite server and surf your way to `localhost:8080`

## Tech stack

* html
* javascript (with JQuery & stomp-websocket library)
* Java (Spring Boot Framework)

Communciation between the server and client is based on websocket technology. The server continuously polls `file.txt` 
and pushes the gathered information to the client.

## Next Versions

The following updates still needed:

1. Add unit tests in order to ensure quality of the code
2. Add runtime configuration of the server. It should be possible to change polling sequence during runtime. This can be done for example with [Spring Cloud Config](https://github.com/spring-cloud/spring-cloud-config) library. 
