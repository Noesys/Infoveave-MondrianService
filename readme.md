# Infoveave Mondiran Connector Service

&copy; 2015-2016, Noesys Software Pvt Ltd. 

Dual Licensed under Infoveave Commercial and AGPLv3

You should have received a copy of the GNU Affero General Public License v3
along with this program (Infoveave)
You can be released from the requirements of the license by purchasing
a commercial license. Buying such a license is mandatory as soon as you
develop commercial activities involving the Infoveave without
disclosing the source code of your own applications.

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
