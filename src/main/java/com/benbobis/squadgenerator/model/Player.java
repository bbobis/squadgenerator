package com.benbobis.squadgenerator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Player {
    @JsonProperty("_id")
    private String id;

    private String firstName;

    private String lastName;

    private List<Skill> skills;

    public Player() {
    }

    public Player(String id, String firstName, String lastName, List<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
    }

    public Player(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        //Returns the skills sorted based on the skill order configured in the SkillType enum
        return skills.stream().sorted(Comparator.comparing(skill -> skill.getType().getOrder())).collect(Collectors.toList());
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public Optional<Skill> getSkill(SkillType type) {
        return skills.stream().filter(skill -> skill.getType().equals(type)).findAny();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
