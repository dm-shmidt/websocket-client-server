# Web socket client-server

# Pre-requisites
**Up and running postgres with:**
* db: wbs_db
* user: postgres
* password: postgres

**Installed Node.js version v18.19.0**

## How to build
From **websocket-client-server** module run 
* mvn clean install

## Running

* java -jar wbs-server/target/wbs-server-1.0.jar
* java -jar wbs-client/target/wbs-client-1.0.jar
* from web-ui directory: ng -serve -o

**Note: for Chrome browser paste https://localhost:8001/cpu-usage/1 and confirm accepting self-signed 
certificate; do the same with https://localhost:8002/run/10 for the same reason.** 

## Using

On the front-end (WebUi page in browser) follow the instructions in the text-boxes.

