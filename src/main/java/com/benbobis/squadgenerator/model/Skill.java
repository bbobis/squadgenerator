package com.benbobis.squadgenerator.model;

public class Skill {
    private SkillType type;
    private int rating;

    public Skill() {
    }

    public Skill(SkillType type, int rating) {
        this.type = type;
        this.rating = rating;
    }

    public SkillType getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }
}
