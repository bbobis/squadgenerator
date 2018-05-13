package com.benbobis.squadgenerator.unit.service;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.model.*;
import com.benbobis.squadgenerator.service.PlayerServiceImpl;
import com.benbobis.squadgenerator.service.client.PlayerAPIClient;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private PlayerAPIClient playerAPIClient;

    @Mock
    private ObjectMapper objectMapper;

    private PlayerServiceImpl playerService;

    private Player alex;

    private Player bob;

    private Player roy;

    private Player jennifer;

    private Player jill;

    private List<Player> listOfAllPlayers;

    private PlayersData playersData;

    @Before
    public void setup() {
        playerService = new PlayerServiceImpl(false, objectMapper, playerAPIClient);

        //players
        alex = new Player(UUID.randomUUID().toString(), "Alex", "Carney", Arrays.asList(
                new Skill(SkillType.SKATING, 90),
                new Skill(SkillType.SHOOTING, 98),
                new Skill(SkillType.CHECKING, 92)
        ));

        bob = new Player(UUID.randomUUID().toString(), "Bob", "Smith", Arrays.asList(
                new Skill(SkillType.SKATING, 80),
                new Skill(SkillType.SHOOTING, 60),
                new Skill(SkillType.CHECKING, 50)
        ));

        roy = new Player(UUID.randomUUID().toString(), "Roy", "Talbot", Arrays.asList(
                new Skill(SkillType.SKATING, 60),
                new Skill(SkillType.SHOOTING, 85),
                new Skill(SkillType.CHECKING, 20)
        ));

        jennifer = new Player(UUID.randomUUID().toString(), "Jennifer", "Wu", Arrays.asList(
                new Skill(SkillType.SKATING, 94),
                new Skill(SkillType.SHOOTING, 55),
                new Skill(SkillType.CHECKING, 100)
        ));

        jill = new Player(UUID.randomUUID().toString(), "Jill", "White", Arrays.asList(
                new Skill(SkillType.SKATING, 70),
                new Skill(SkillType.SHOOTING, 90),
                new Skill(SkillType.CHECKING, 60)
        ));

        listOfAllPlayers = Arrays.asList(alex, bob, roy, jennifer, jill);
        playersData = new PlayersData(listOfAllPlayers);
    }

    @Test
    public void should_return_all_players_as_wait_listed_when_no_squads_are_generated() throws Exception {
        //given
        when(objectMapper.readValue(anyString(), eq(PlayersData.class))).thenReturn(playersData);

        //when
        List<Player> result = playerService.getWaitingList(new ArrayList<>());

        //then
        then(result).isNotEmpty();
        then(result).contains(alex, bob, roy);
    }

    @Test
    public void should_filter_out_players_allocated_to_a_squad_from_the_waiting_list() throws Exception {
        //given
        Squad squad1 = new Squad(Arrays.asList(alex, roy));
        Squad squad2 = new Squad(Arrays.asList(jennifer, jill));
        when(objectMapper.readValue(anyString(), eq(PlayersData.class))).thenReturn(playersData);

        //when
        List<Player> result = playerService.getWaitingList(Arrays.asList(squad1, squad2));

        //then
        then(result).isNotEmpty();
        then(result).containsOnly(bob);
    }

    @Test
    public void should_create_squad_for_bob_and_jill_and_squad_for_jennifer_and_roy() throws Exception {
        //given
        when(objectMapper.readValue(anyString(), eq(PlayersData.class))).thenReturn(playersData);

        //when
        List<Squad> results = playerService.generateSquad(2);

        //then
        Condition<Squad> bobAndJillSquad = new Condition<>(squad -> squad.getPlayers().containsAll(Arrays.asList(bob, jill)), "Bob and Jill Squad");
        Condition<Squad> jenniferAndRoySquad = new Condition<>(squad -> squad.getPlayers().containsAll(Arrays.asList(jennifer, roy)), "Jennifer and Roy Squad");

        then(results).hasSize(2);
        then(results.get(0)).has(anyOf(bobAndJillSquad, jenniferAndRoySquad));
        then(results.get(1)).has(anyOf(bobAndJillSquad, jenniferAndRoySquad));
    }

    @Test
    public void should_throw_PlayerDataRetrievalException_when_failed_to_communicate_with_api() throws Exception {
        //given
        playerService = new PlayerServiceImpl(true, objectMapper, playerAPIClient);
        when(playerAPIClient.getPlayersData()).thenThrow(FeignException.class);

        thrown.expect(PlayerDataRetrievalException.class);

        //when
        playerService.getWaitingList(new ArrayList<>());
    }

    @Test
    public void should_throw_PlayerDataRetrievalException_when_failed_to_parse_json_file() throws Exception {
        //given
        when(objectMapper.readValue(anyString(), eq(PlayersData.class))).thenThrow(JsonParseException.class);

        thrown.expect(PlayerDataRetrievalException.class);

        //when
        playerService.getWaitingList(new ArrayList<>());
    }

    @Test
    public void should_throw_PlayerDataRetrievalException_when_failed_to_bind_json_data_to_object() throws Exception {
        //given
        when(objectMapper.readValue(anyString(), eq(PlayersData.class))).thenThrow(JsonMappingException.class);

        thrown.expect(PlayerDataRetrievalException.class);

        //when
        playerService.getWaitingList(new ArrayList<>());
    }
}
