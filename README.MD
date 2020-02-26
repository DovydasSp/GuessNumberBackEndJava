# Guess Number API

## Table of contents
* [General info](#general-info)
* [Setup](#setup)

## General info
Simple server API for Guess Number game.
Default port it runs on is 4568
	
## Setup
To clone and run this application, you'll need [Git](https://git-scm.com) and [Intellij IDEA](https://www.jetbrains.com/help/idea/installation-guide.html#standalone). From your command line:

```
# Clone this repository
$ git clone https://github.com/DovydasSp/GuessNumberBackEndJava

# Go into the repository
$ cd GuessNumberBackEndJava

# Create docker postgresql container
$ docker run --name postgresql-guessnumber -e POSTGRES_PASSWORD=guessnumber -d -p 5432:5432 -v ./postgresql postgres

# Build project
$ ./gradlew installDist

# Run the app
$ cd ./main/build/install/guess-number-api/bin/guess-number-api
```
