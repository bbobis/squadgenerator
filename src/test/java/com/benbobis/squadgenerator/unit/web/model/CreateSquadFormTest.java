package com.benbobis.squadgenerator.unit.web.model;

import com.benbobis.squadgenerator.web.model.CreateSquadForm;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class CreateSquadFormTest {
    @Test
    public void should_generate_values_1_2_3_when_waiting_list_size_is_6() {
        //given
        CreateSquadForm form = new CreateSquadForm(6, 0);

        //when
        List<Integer> possibleNumbersOfSquad = form.getPossibleNumberOfSquads();

        //then
        then(possibleNumbersOfSquad).containsExactly(1, 2, 3);
    }

    @Test
    public void should_generate_values_1_2_3_when_waiting_list_size_is_7() {
        //given
        CreateSquadForm form = new CreateSquadForm(7, 0);

        //when
        List<Integer> possibleNumbersOfSquad = form.getPossibleNumberOfSquads();

        //then
        then(possibleNumbersOfSquad).containsExactly(1, 2, 3);
    }

    @Test
    public void should_default_to_1_when_passing_0_for_numberOfSquadsCreated() {
        //given
        CreateSquadForm form = new CreateSquadForm(0, 0);

        //when
        Integer numberOfSquadsToCreate = form.getNumberOfSquadsToCreate();

        //then
        then(numberOfSquadsToCreate).isEqualTo(1);
    }

    @Test
    public void should_take_numberOfSquadsCreated_value_for_anything_greater_than_0() {
        //given
        CreateSquadForm form = new CreateSquadForm(0, 10);

        //when
        Integer numberOfSquadsToCreate = form.getNumberOfSquadsToCreate();

        //then
        then(numberOfSquadsToCreate).isEqualTo(10);
    }
}
