# Squad Generator
ATB Financial Squad Maker Challenge Submission

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