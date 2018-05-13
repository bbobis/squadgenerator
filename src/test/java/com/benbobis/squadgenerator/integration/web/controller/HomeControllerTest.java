package com.benbobis.squadgenerator.integration.web.controller;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.service.PlayerService;
import com.benbobis.squadgenerator.web.controller.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void should_return_status_code_200_when_navigating_to_home_page() throws Exception {
        //given
        given(this.playerService.getWaitingList(any())).willReturn(new ArrayList<>());

        //when & then
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void should_return_status_code_503_when_PlayerDataRetrievalException_occurs() throws Exception {
        //given
        given(this.playerService.getWaitingList(any())).willThrow(PlayerDataRetrievalException.class);

        //when & then
        mvc.perform(get("/")).andExpect(status().isServiceUnavailable());
    }
}
