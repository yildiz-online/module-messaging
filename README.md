# Yildiz-Engine module messaging

This is the official repository of the messaging module, part of the Yildiz-Engine project.
The messaging module is a set of classes to send and receive message asynchronously.

## Features

* Embedded broker.
* Send text messages.
* Receive text message aynchronously.
* ...

## Requirements

To build this module, you will need a java 8 JDK and Maven 3.

## Coding Style and other information

Project website:
http://www.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarcloud.io/dashboard?id=be.yildiz-games%3Amodule-messaging

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.

## Build instructions

Go to your root directory, where you POM file is located.

Then invoke maven

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file.

## Usage

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>module-messaging</artifactId>
    <version>LATEST</version>
</dependency>
```

Replace LATEST with the version number to use.

## Contact
Owner of this repository: Grégory Van den Borre
