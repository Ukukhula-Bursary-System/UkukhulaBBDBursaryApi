package com.bbd.bursary.manager.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableScheduling
public class Configaration {
    @Scheduled(fixedDelay = (1000 * 60 * 10)) // every 10 minutes make a call to the api
    public void scheduleFixedDelayTask() {
        try {
            String apiEndpoint = "https://ukukhulabbdbursaryapi.onrender.com";
            RestTemplate template = new RestTemplate();
            template.getForObject(apiEndpoint, String.class);
            System.out.println("Refreshing api");
        }
        catch (Exception e){
            System.out.println("Refreshing api");
        }

    }
}




