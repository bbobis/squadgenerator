package com.benbobis.squadgenerator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SkillType {

    @JsonProperty("Skating") SKATING(0),
    @JsonProperty("Shooting") SHOOTING(1),
    @JsonProperty("Checking") CHECKING(2);

    private int order;

    SkillType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
