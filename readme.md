# Infoveave Mondiran Connector Service
&copy; 2016 Noesys Software Pvt.Ltd

Author : Naresh Jois <naresh@noesyssoftware.com>

----

### Introduction
A Spark based Micro HTTP Server which acts as a middleware between Infoveave 
Application and Mondiran based Data sources.

### Pre-requisites

- Java 1.8 or above
- Maven
- IntelliJ Idea (Preferred)

### Build

- Open Project
- Restore Maven Dependencies
- Generate Artifacts

### Configure

   Generated Artifacts has a single properties file `MondrianService.properties`
   - Port : num [Port Number on which HTTP Server Runs]
   - MaxThreads : num [No of threads which the Spark Server can run on]


### Run

    $ java -jar MondrianService.jar
    
### TODO

- [X] - MySQL Implementation
- [X] - MSSQL Implementation
- [X] - Postgres SQL Implementation
- [X] - SQLite Implementation
 
### Change log

- 2015-12-23 : Initial Commit with MySQL and MSSQL
- 2016-02-04 : Added Postgres and SQLite