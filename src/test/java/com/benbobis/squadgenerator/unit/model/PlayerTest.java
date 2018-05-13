package com.benbobis.squadgenerator.unit.model;

import com.benbobis.squadgenerator.model.Player;
import com.benbobis.squadgenerator.model.Skill;
import com.benbobis.squadgenerator.model.SkillType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

public class PlayerTest {
    @Test
    public void should_return_list_of_skills_in_following_order_skating_shooting_checking() {
        //given
        Skill skating = new Skill(SkillType.SKATING, 10);
        Skill shooting = new Skill(SkillType.SHOOTING, 10);
        Skill checking = new Skill(SkillType.CHECKING, 10);
        List<Skill> playerSkills = Arrays.asList(checking, shooting, skating);
        Player john = new Player("", "John", "Doe", playerSkills);

        //when
        List<Skill> results = john.getSkills();

        //then
        then(results).containsExactly(skating, shooting, checking);
    }

    @Test
    public void should_return_concatenated_firstname_space_lstname_as_fullname() {
        //given
        Player john = new Player("", "John", "Doe", new ArrayList<>());

        //when
        String result = john.getFullName();

        //then
        then(result).isEqualTo("John Doe");
    }

    @Test
    public void should_return_correct_skill_for_given_type() {
        //given
        Skill skating = new Skill(SkillType.SKATING, 10);
        Skill shooting = new Skill(SkillType.SHOOTING, 20);
        Skill checking = new Skill(SkillType.CHECKING, 30);
        List<Skill> playerSkills = Arrays.asList(checking, shooting, skating);
        Player john = new Player("", "John", "Doe", playerSkills);

        //when
        Optional<Skill> result = john.getSkill(SkillType.CHECKING);

        //then
        then(result.isPresent()).isTrue();
        then(result.get().getType()).isEqualTo(SkillType.CHECKING);
        then(result.get().getRating()).isEqualTo(30);
    }

    @Test
    public void should_return_true_when_equality_is_checked_on_players_with_same_id() {
        //given
        Player john = new Player("ID-123", "John", "Doe", new ArrayList<>());
        Player johnsClone = new Player("ID-123", "John", "Doe", new ArrayList<>());

        //when
        boolean isEqual = john.equals(johnsClone);

        //then
        then(isEqual).isTrue();
    }

    @Test
    public void should_return_false_when_equality_is_checked_on_players_with_different_id() {
        //given
        Player john = new Player("ID-123", "John", "Doe", new ArrayList<>());
        Player jane = new Player("ID-345", "Jane", "Doe", new ArrayList<>());

        //when
        boolean isEqual = john.equals(jane);

        //then
        then(isEqual).isFalse();
    }
}
