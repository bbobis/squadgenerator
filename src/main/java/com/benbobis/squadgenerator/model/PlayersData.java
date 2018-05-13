package com.benbobis.squadgenerator.model;

import java.util.List;

public class PlayersData {
    private List<Player> players;

    public PlayersData() {}

    public PlayersData(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
