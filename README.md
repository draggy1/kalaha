# Kalaha game

Java RESTful Web Service that runs a game of 6-stone Kalah. This web service enables to let 2 human players to play the game, each in his own computer.

## Kalah Rules
Each of the two players has ** six pits ** in front of him/her. To the right of the six pits, each player has a larger pit, his Kalah or house. At the start of the game, six stones are put in each pit. The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other player's turn. When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalah. The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.


## Installation
Kalaha game is a Spring boot application requires Java 15 and Maven. 
App can be run from any environment i.e Intellij Idea
Here is presented how to run app from command line

First build Spring Boot Project with Maven. Enter to the project directory (kalaha) where is located pom file
If that fails, may be a problem with .m2 file
```sh
mvn clean install
```
Run Spring Boot file using Maven:

```sh
mvn spring-boot:run
```
Optional: Run Spring Boot app with java -jar command 
```sh
java -jar target/kalaha-0.0.1-SNAPSHOT.jar
```



## Domain language
- pit - contains stones
- home pit - the right, larger pit dedicated to concrete player 
- an ordinary pit - the one from the group of pits in front of the player
- ordinary pit number - configurable value describes how many ordinary pits are in the game
- game - single game between two players
- game board - area of the game where are moved stones
- head - a first ordinary pit with number 1
- current pit - current handled pit
- next pit - the first pit after current in normal order (except home pit of player two - a first ordinary pit with number 1 is next for home pit of player two)
- opposite pit - the pit from where are grabed stones when last stone landed to the empty pit. Number of opposite pit can be calculated by subtracting current number of pit from home pit of player two.
- owner - player who the pit belongs to
- making move - process of scattering stones around the board performed by single player

## Structure

![Structure image](Structure.png?raw=true "Structure")

## License

MIT
