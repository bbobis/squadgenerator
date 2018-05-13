package com.benbobis.squadgenerator.service;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.model.Player;
import com.benbobis.squadgenerator.model.PlayersData;
import com.benbobis.squadgenerator.model.Squad;
import com.benbobis.squadgenerator.service.client.PlayerAPIClient;
import com.benbobis.squadgenerator.service.helper.SquadConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_JSON_FILE_LOCATION = "classpath:data/players.json";

    private final boolean enablePlayerServiceIntegration;

    private final ObjectMapper objectMapper;

    private final PlayerAPIClient playerAPIClient;

    public PlayerServiceImpl(@Value("${player.api.enable}") boolean enablePlayerServiceIntegration, ObjectMapper objectMapper, PlayerAPIClient playerAPIClient) {
        this.enablePlayerServiceIntegration = enablePlayerServiceIntegration;
        this.objectMapper = objectMapper;
        this.playerAPIClient = playerAPIClient;
    }

    /**
     * Gets a list of players that haven't been allocated to a squad by filtering out all players, already allocated to a squad, from the list of all players.
     * @param existingSquads - list of existing squads.
     * @return list of players that doesn't belong to a squad
     * @throws PlayerDataRetrievalException
     */
    @Override
    public List<Player> getWaitingList(List<Squad> existingSquads) throws PlayerDataRetrievalException {
        List<Player> waitingList = this.getPlayers();

        //If there are already existing squads, filter them out of the waiting list.
        if (!existingSquads.isEmpty()) {
            return waitingList
                    .stream()
                    .filter(playerInWaitingList ->
                            existingSquads
                                    .stream()
                                    .flatMap(squad -> squad.getPlayers().stream())
                                    .noneMatch(playerInWaitingList::equals))
                    .collect(Collectors.toList());
        }

        return waitingList;
    }

    /**
     * Generates n number of squads by generating squad configurations multiple times and selecting the best configuration to use based on the configuration score
     * @param numberOfSquad - number of squads to generate
     * @return list of squads generated
     * @throws PlayerDataRetrievalException
     */
    @Override
    public List<Squad> generateSquad(int numberOfSquad) throws PlayerDataRetrievalException {
        List<Player> players = this.getPlayers();

        SquadConfiguration bestConfiguration = generateSquadConfiguration(players, numberOfSquad);
        //No need to run generation of squad configuration for a squad of 1
        if (numberOfSquad != 1) {
            //Chose to run the squad generation for 1000 iterations to find a better combination
            for (int i = 0; i < 1000; i++) {
                //Generate a squad configuration and compare the new config's spread factor to the old config's spread factor
                //Less spread factor the better.
                SquadConfiguration newSquadConfiguration = generateSquadConfiguration(players, numberOfSquad);
                if (newSquadConfiguration.getConfigurationScore() > bestConfiguration.getConfigurationScore()) {
                    bestConfiguration = newSquadConfiguration;
                }
            }
        }

        return bestConfiguration.getSquads();
    }

    /**
     * Retrieves player data from JSON payload either from the JSON file or the Player API service
     * @return list of players retrieved
     * @throws PlayerDataRetrievalException
     */
    private List<Player> getPlayers() throws PlayerDataRetrievalException {
        //Based on the configuration, determine if we should use the PlayersData API or the PlayersData JSON file
        //to get all the players data
        if (this.enablePlayerServiceIntegration) {
            try {
                return playerAPIClient.getPlayersData().getPlayers();
            } catch (FeignException ex) {
                throw new PlayerDataRetrievalException(ex.getMessage());
            }
        } else {
            return getPlayersDataFromJSONFile().getPlayers();
        }
    }

    /**
     * Creates a squad configuration containing n number of generated squads.
     * Each squad is populated by players randomly
     * @param playerWaitingList - lists of all players waiting to be allocated to a squad
     * @param numberOfSquads - number of squads to generate
     * @return the squad configuration
     */
    private SquadConfiguration generateSquadConfiguration(List<Player> playerWaitingList, int numberOfSquads) {
        List<Player> listOfPlayersToBeAllocated = new ArrayList<>(playerWaitingList);
        Random rand = new Random();

        //Get the number of players per squad
        int numberOfPlayersPerSquad = listOfPlayersToBeAllocated.size() / numberOfSquads;

        List<Squad> generatedSquads = new ArrayList<>();

        //Create N number of squads based on the passed numberOfSquads
        for (int i = 0; i < numberOfSquads; i++) {
            List<Player> listOfPlayersForASquad = new ArrayList<>();
            for (int j = 0; j < numberOfPlayersPerSquad; j++) {
                //For each squad, fill it up with players randomly.
                int randomPlayerIndex = rand.nextInt(listOfPlayersToBeAllocated.size());
                listOfPlayersForASquad.add(listOfPlayersToBeAllocated.remove(randomPlayerIndex));
            }
            generatedSquads.add(new Squad(listOfPlayersForASquad));
        }

        return new SquadConfiguration(generatedSquads);
    }

    /**
     * Read and binds the player data from the given JSON file
     * @return PlayerData object that represents the JSON payload
     * @throws PlayerDataRetrievalException
     */
    private PlayersData getPlayersDataFromJSONFile() throws PlayerDataRetrievalException {
        try {
            //Get the file
            File file = ResourceUtils.getFile(PLAYERS_JSON_FILE_LOCATION);

            //Read the contents of the file
            String content = new String(Files.readAllBytes(file.toPath()));

            //Map the JSON content to the PlayersData class
            return objectMapper.readValue(content, PlayersData.class);
        } catch (IOException ex) {
            throw new PlayerDataRetrievalException(ex.getMessage());
        }
    }
}
