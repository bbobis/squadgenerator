# Squad Generator by Benedict Bobis
ATB Financial Squad Maker Challenge Submission

## Approach 
The steps for the approach taken for the algorithm are the following:
1. Populate the desired number of squads by randomly allocating players until all squads are full.
1. Determine a score for this configuration.
    * How I determine the score for a configuration is based on the averages per skill:
        1. For each skill, I take the squad with the highest average and subtract that average from the squad with the lowest average to get the amount of "spread value" this configuration has for a skill.
        1. Once i have the spread value for each of the skill, I score them one by one using the following rules:
            * 0 spread value = 5 pts.
            * 1-5 spread value = 4 pts.
            * 6-10 spread value = 3 pts.
            * 11-15 spread value = 2 pts.
            * 16-20 spread value = 1 pt.
            * Any spread value greater than 20 = no pts.
        1. I add up all the points generated for the 3 skills and that becomes the configurations score.
1.  I run multiple iterations (1000 times) while keeping track of the best configuration generated and updating when a new "best" has been generated.
1. Once the iteration is done, I assume that what I have is balanced enough.

***Obviously this is by no means the best and optimized approach but this does generate some balanced squads***

## Running the application using *Maven*
### Prerequisite:
* [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Ensure JAVA_HOME environment variable is set and points to your JDK installation

**Step 1:** Clone the project.

**Step 2:** On the root directory of the project, open up a terminal and run the following command:

*You might need to add execute permission on the "mvnw" file when running on linux before you can run the command.*
````
./mvnw spring-boot:run
````
To integrate with the player service api, you need to provide 3 arguments to the command:
* player.api.enable=true
* player.api.url= *Provide the url for your player service*
* player.api.endpoint= *Provide the endpoint that returns the PlayerData JSON payload*

***I made the assumption that the api does not require any security token to be passed in or any security mechanism***
````
# I'm using the following values for this example:
# * player.api.enable=true
# * player.api.url=http://myplayerservice.com
# * player.api.endpoint=/getPlayers

./mvnw spring-boot:run -Dspring-boot.run.arguments=--player.api.enable=true,--player.api.url=http://myplayerservice.com,--player.api.endpoint=/getPlayers
````

**Step 3:** On your browser, navigate to [localhost:8080](http://localhost:8080)

## Technology Stack
* Spring Boot, Spring MVC
* Feign Client
* Thymeleaf
* HTML5, CSS, JavaScript, jQuery
* Junit