package com.benbobis.squadgenerator.service.client;

import com.benbobis.squadgenerator.model.PlayersData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "playerapi", url = "${player.api.url}")
public interface PlayerAPIClient {
    @GetMapping(value = "${player.api.endpoint}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    PlayersData getPlayersData();
}
