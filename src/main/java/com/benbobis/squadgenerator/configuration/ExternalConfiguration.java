package com.benbobis.squadgenerator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "squadgenerator")
public class ExternalConfiguration {
    private boolean usePlayerApi;

    public boolean usePlayerApi() {
        return usePlayerApi;
    }

    public void setUsePlayerApi(boolean usePlayerApi) {
        this.usePlayerApi = usePlayerApi;
    }
}
