package com.example.twitterAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Component
public class TwitterGenerator {

    @Bean
    public Twitter createNewTwitter(){
        return TwitterFactory.getSingleton();
    }

}
