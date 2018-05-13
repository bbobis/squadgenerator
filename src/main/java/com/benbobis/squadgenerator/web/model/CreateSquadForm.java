package com.benbobis.squadgenerator.web.model;

import java.util.ArrayList;
import java.util.List;

public class CreateSquadForm {
    private int numberOfSquadsToCreate;
    private List<Integer> possibleNumberOfSquads;

    public CreateSquadForm(int numberOfPlayersInWaitingList, int numberOfSquadsCreated) {
        this.numberOfSquadsToCreate = numberOfSquadsCreated == 0 ? 1 : numberOfSquadsCreated;
        this.possibleNumberOfSquads = getPossibleNumberOfSquads(numberOfPlayersInWaitingList);
    }

    public int getNumberOfSquadsToCreate() {
        return numberOfSquadsToCreate;
    }

    public void setNumberOfSquadsToCreate(int numberOfSquadsToCreate) {
        this.numberOfSquadsToCreate = numberOfSquadsToCreate;
    }

    public List<Integer> getPossibleNumberOfSquads() {
        return possibleNumberOfSquads;
    }

    public void setPossibleNumberOfSquads(List<Integer> possibleNumberOfSquads) {
        this.possibleNumberOfSquads = possibleNumberOfSquads;
    }

    private List<Integer> getPossibleNumberOfSquads(int numberOfPlayersInWaitingList) {
        List<Integer> possibleNumberOfSquads = new ArrayList<>();

        for (int i = 1; i <= numberOfPlayersInWaitingList / 2; i++) {
            possibleNumberOfSquads.add(i);
        }

        return possibleNumberOfSquads;
    }
}
