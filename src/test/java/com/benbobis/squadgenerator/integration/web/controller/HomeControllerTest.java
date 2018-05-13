package com.benbobis.squadgenerator.integration.web.controller;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.service.PlayerService;
import com.benbobis.squadgenerator.web.controller.HomeController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private PlayerService playerService;

    @Test
    public void should_return_index_page_when_navigating_to_home_url() throws Exception {
        given(this.playerService.getWaitingList(any()))
                .willReturn(new ArrayList<>());

        //when
        HtmlPage page = webClient.getPage("/");
        DomNodeList<DomElement> elements = page.getElementsByTagName("title");

        //them
        then(elements).hasSize(1);
        then(elements.get(0).getTextContent()).isEqualTo("Squad Generator");
    }

    @Test
    public void should_return_error_page_when_navigating_to_home_url_and_player_exception_occurred() throws Exception {
        given(this.playerService.getWaitingList(any()))
                .willThrow(PlayerDataRetrievalException.class);

        //when
        HtmlPage page = webClient.getPage("/");
        DomNodeList<DomElement> elements = page.getElementsByTagName("title");

        //them
        then(elements).hasSize(1);
        then(elements.get(0).getTextContent()).isEqualTo("Error");
    }
}
