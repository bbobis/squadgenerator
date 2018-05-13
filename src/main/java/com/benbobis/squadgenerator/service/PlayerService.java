package com.benbobis.squadgenerator.service;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.model.Player;
import com.benbobis.squadgenerator.model.Squad;

import java.util.List;

public interface PlayerService {
    List<Player> getWaitingList(List<Squad> existingSquads) throws PlayerDataRetrievalException;

    List<Squad> generateSquad(int numberOfSquad) throws PlayerDataRetrievalException;
}
