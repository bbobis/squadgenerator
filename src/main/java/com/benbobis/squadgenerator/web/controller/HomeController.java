package com.benbobis.squadgenerator.web.controller;

import com.benbobis.squadgenerator.exception.PlayerDataRetrievalException;
import com.benbobis.squadgenerator.service.PlayerService;
import com.benbobis.squadgenerator.web.model.CreateSquadForm;
import com.benbobis.squadgenerator.web.model.Home;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
    private final PlayerService playerService;

    public HomeController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public String home(@ModelAttribute Home home) throws PlayerDataRetrievalException {
        home.setWaitingList(playerService.getWaitingList(home.getSquads()));
        home.setForm(new CreateSquadForm(home.getWaitingList().size(), home.getSquads().size()));
        return "home";
    }

    @PostMapping
    public String createSquad(@ModelAttribute Home home, RedirectAttributes redirectAttrs) throws PlayerDataRetrievalException {
            home.setSquads(playerService.generateSquad(home.getForm().getNumberOfSquadsToCreate()));
        redirectAttrs.addFlashAttribute("home", home);
        return "redirect:/";
    }
}
