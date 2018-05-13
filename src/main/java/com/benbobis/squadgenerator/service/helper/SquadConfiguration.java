package com.benbobis.squadgenerator.service.helper;

import com.benbobis.squadgenerator.model.Squad;

import java.util.IntSummaryStatistics;
import java.util.List;

public class SquadConfiguration {
    private List<Squad> squads;

    private int configurationScore;

    public SquadConfiguration(List<Squad> squads) {
        this.squads = squads;
        this.configurationScore = calculateConfigurationScore();
    }

    public List<Squad> getSquads() {
        return squads;
    }

    public int getConfigurationScore() {
        return configurationScore;
    }

    /**
     * This determines the total score for the configuration based on the difference between the upper bound
     * and the lower bound per each skill average
     * @return the total score for configuration
     */
    private int calculateConfigurationScore() {
        IntSummaryStatistics skatingStats = squads.stream().mapToInt(Squad::getAverageSkillRatingForSkating).summaryStatistics();
        IntSummaryStatistics shootingStats = squads.stream().mapToInt(Squad::getAverageSkillRatingForShooting).summaryStatistics();
        IntSummaryStatistics checkingStats = squads.stream().mapToInt(Squad::getAverageSkillRatingForChecking).summaryStatistics();

        int skatingScore = generateScoreBasedOnSpreadFactor(skatingStats.getMax() - skatingStats.getMin());
        int shootingScore = generateScoreBasedOnSpreadFactor(shootingStats.getMax() - shootingStats.getMin());
        int checkingScore = generateScoreBasedOnSpreadFactor(checkingStats.getMax() - checkingStats.getMin());

        return skatingScore + shootingScore + checkingScore;
    }

    /**
     * This generates a score based on the amount of spread (difference between upper and lower bound per each skill average).
     * Less spread generates better score
     * A spread of 0 gets a score of 5
     * A spread between 1 and 5 gets a score of 4
     * A spread between 6 and 10 gets a score of 3
     * A spread between 11 and 15 gets a score of 2
     * A spread between 16 and 20 gets a score of 1
     * A spread greater than 20 gets no points
     * @param spreadFactor - the difference between the upper and lower bound for a skill rating average
     * @return the total score for the spread value input
     */
    private int generateScoreBasedOnSpreadFactor(int spreadFactor) {
        //Less spread between the upper and the lower bound has more score.
        int totalScore = 0;

        if (spreadFactor == 0){
            totalScore += 5;
        } else if (spreadFactor > 0 && spreadFactor <= 5) {
            totalScore += 4;
        } else if (spreadFactor > 5 && spreadFactor <= 10) {
            totalScore += 3;
        } else if (spreadFactor > 10 && spreadFactor <= 15) {
            totalScore += 2;
        } else if (spreadFactor > 15 && spreadFactor <= 20) {
            totalScore += 1;
        } else {
            totalScore += 0;
        }

        return totalScore;
    }
}
