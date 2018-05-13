package com.benbobis.squadgenerator.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Squad {
    private List<Player> players;

    private int averageSkillRatingForSkating;

    private int averageSkillRatingForShooting;

    private int averageSkillRatingForChecking;

    public Squad(List<Player> players) {
        this.players = players;
        averageSkillRatingForSkating = getAveragePerSkillType(SkillType.SKATING);
        averageSkillRatingForShooting = getAveragePerSkillType(SkillType.SHOOTING);
        averageSkillRatingForChecking = getAveragePerSkillType(SkillType.CHECKING);
    }

    public List<Player> getPlayers() {
        return players.stream().sorted(Comparator.comparing(Player::getFullName)).collect(Collectors.toList());
    }

    public int getAverageSkillRatingForSkating() {
        return averageSkillRatingForSkating;
    }

    public int getAverageSkillRatingForShooting() {
        return averageSkillRatingForShooting;
    }

    public int getAverageSkillRatingForChecking() {
        return averageSkillRatingForChecking;
    }

    public double getPercentage(SkillType type) {
        double total = averageSkillRatingForSkating + averageSkillRatingForShooting + averageSkillRatingForChecking;
        double percentage = 0.0;

        if (total > 0) {
            switch (type) {
                case SKATING:
                    percentage = (averageSkillRatingForSkating / total) * 100;
                    break;
                case SHOOTING:
                    percentage = (averageSkillRatingForShooting / total) * 100;
                    break;
                case CHECKING:
                    percentage = (averageSkillRatingForChecking / total) * 100;
                    break;
            }
        }

        return percentage;
    }

    private int getAveragePerSkillType(SkillType type) {
        //Rounding down by casting to int.
        return (int) players.stream().mapToInt(player -> player.getSkill(type).map(Skill::getRating).orElse(0)).average().orElse(Double.NaN);
    }
}
