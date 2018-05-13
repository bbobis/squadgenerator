package com.benbobis.squadgenerator.web.model;

import com.benbobis.squadgenerator.model.Player;
import com.benbobis.squadgenerator.model.Squad;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private List<Player> waitingList;

    private List<Squad> squads;

    private CreateSquadForm form;

    public Home() {
        waitingList = new ArrayList<>();
        squads = new ArrayList<>();
        form = new CreateSquadForm(0, 0);
    }

    public List<Player> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<Player> waitingList) {
        this.waitingList = waitingList;
    }

    public List<Squad> getSquads() {
        return squads;
    }

    public void setSquads(List<Squad> squads) {
        this.squads = squads;
    }

    public CreateSquadForm getForm() {
        return form;
    }

    public void setForm(CreateSquadForm form) {
        this.form = form;
    }
}
