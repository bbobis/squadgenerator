# Squad Generator
ATB Financial Squad Maker Challenge Submission

## Approach 
The steps for the approach taken for the algorithm are the following:
1. Populate the desired number of squads by randomly allocating players until all squads are full.
1. Determine a score for this configuration.
    * How I determine the score for a configuration is based on the averages per skill:
        1. For each skill, I take the squad with the highest average and subtract that average from the squad with the lowest average to get the amount of "spread" this configuration has for a skill.
        1. Once i have the spread value for each of the skill, I score them one by one using the following rules:
            * 0 spread = 5 pts.
            * 1-5 spread = 4 pts.
            * 6-10 spread = 3 pts.
            * 11-15 = 2 pts.
            * 16-20 = 1 pts.
            * Any spread greater than 20 = no pts.
        1. I add up all the points generated for the 3 skills and that becomes the configurations score
1.  I run multiple iterations (1000 times) while keeping track of the best configuration generated and updating when a new "best" has been generated.
1. Once the iteration is done, I assumed that what I have is balanced enough.

## Running the application using *Maven*
The command provided here is used to run the application through maven. You do not need to download or setup maven if you don't have it.

You **need** to have Java 8 installed and JAVA_HOME system environment variable is setup. 

Run the following command on a command line to run the application.
````
./mvnw spring-boot:run
````
To integrate with the player service api, you need to provide 3 arguments when running the application:
* squadgenerator.useplayerapi=true
* player.api.url= *Provide the url for your player service*
* player.api.endpoint= *Provide the endpoint that returns the PlayerData JSON payload*

***I made the assumption that the api does not require any security token to be passed in or any security mechanism***
````
# I'm using the following values for this example:
# * squadgenerator.useplayerapi=true
# * player.api.url=http://myplayerservice.com
# * player.api.endpoint=/getPlayers

./mvnw spring-boot:run -Dspring-boot.run.arguments=--squadgenerator.useplayerapi=true,--player.api.url=http://myplayerservice.com,--player.api.endpoint=/getPlayers
````

The application runs on [Localhost 8080](http://localhost:8080)