# Service Inventory
Management of application servers information (servers, deployment, changes).

## Platform

+ Grails 3.11
+ Gradle
+ Vue 2.1
+ JDK8
+ Embedded Tomcat

## Development

Start backend service and frontend separately
> ./gradlew server:bootRun

From the client subdirectory
> yarn run dev

## Build

> ./gradlew assembleServerAndClient

To run the packaged application:

> java -jar build/server-1.0.jar

## Configuration

By placing a file named application.yml in the same location as the packaged JAR, application parameters can be altered such as the server port to be used.
