package com.benbobis.squadgenerator.unit.model;

import com.benbobis.squadgenerator.model.Player;
import com.benbobis.squadgenerator.model.Skill;
import com.benbobis.squadgenerator.model.SkillType;
import com.benbobis.squadgenerator.model.Squad;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

public class SquadTest {

    private Player john;

    private Player jane;

    private Player joe;

    @Before
    public void setup(){
        john = new Player(UUID.randomUUID().toString(), "John", "Doe");
        jane = new Player(UUID.randomUUID().toString(), "Jane", "Doe");
        joe = new Player(UUID.randomUUID().toString(), "Joe", "Doe");
    }

    @Test
    public void should_return_rounded_down_average_for_skating_skill_rating() {
        //given
        john.setSkills(Collections.singletonList(new Skill(SkillType.SKATING, 11)));
        jane.setSkills(Collections.singletonList(new Skill(SkillType.SKATING, 20)));
        joe.setSkills(Collections.singletonList(new Skill(SkillType.SKATING, 30)));

        Squad squad = new Squad(Arrays.asList(john, jane, joe));

        //when
        int average = squad.getAverageSkillRatingForSkating();

        //then
        then(average).isEqualTo(20);
    }

    @Test
    public void should_return_rounded_down_average_for_shooting_skill_rating() {
        //given
        john.setSkills(Collections.singletonList(new Skill(SkillType.SHOOTING, 11)));
        jane.setSkills(Collections.singletonList(new Skill(SkillType.SHOOTING, 20)));
        joe.setSkills(Collections.singletonList(new Skill(SkillType.SHOOTING, 30)));

        Squad squad = new Squad(Arrays.asList(john, jane, joe));

        //when
        int average = squad.getAverageSkillRatingForShooting();

        //then
        then(average).isEqualTo(20);
    }

    @Test
    public void should_return_rounded_down_average_for_checking_skill_rating() {
        //given
        john.setSkills(Collections.singletonList(new Skill(SkillType.CHECKING, 11)));
        jane.setSkills(Collections.singletonList(new Skill(SkillType.CHECKING, 20)));
        joe.setSkills(Collections.singletonList(new Skill(SkillType.CHECKING, 30)));

        Squad squad = new Squad(Arrays.asList(john, jane, joe));

        //when
        int average = squad.getAverageSkillRatingForChecking();

        //then
        then(average).isEqualTo(20);
    }

    @Test
    public void should_return_correct_percentage_for_skill_against_squads_total_skill_average() {
        //given
        john.setSkills(Arrays.asList(
                new Skill(SkillType.SKATING, 10),
                new Skill(SkillType.SHOOTING, 20),
                new Skill(SkillType.CHECKING, 30)
        ));

        jane.setSkills(Arrays.asList(
                new Skill(SkillType.SKATING, 40),
                new Skill(SkillType.SHOOTING, 40),
                new Skill(SkillType.CHECKING, 60)
        ));

        Squad squad = new Squad(Arrays.asList(john, jane));

        //when
        double skatingPercentage = squad.getPercentage(SkillType.SKATING);
        double shootingPercentage = squad.getPercentage(SkillType.SHOOTING);
        double checkingPercentage = squad.getPercentage(SkillType.CHECKING);

        //then
        then(skatingPercentage).isEqualTo(25);
        then(shootingPercentage).isEqualTo(30);
        then(checkingPercentage).isEqualTo(45);
    }
}
