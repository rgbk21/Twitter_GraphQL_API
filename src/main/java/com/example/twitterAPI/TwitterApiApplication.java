package com.example.twitterAPI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TwitterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo() throws TwitterException, IOException {
//
//		String handle = "realDonaldTrump";
//
//		//Connects to Twitter and performs Authorization
//		Twitter twitter = TwitterFactory.getSingleton();
//
//		List<Status> statuses = new ArrayList<>();
//		Paging page = new Paging(1, 10);
//
//		page.setPage(1);
//
//		statuses.addAll(twitter.getUserTimeline(handle, page));
//
//		for (int i = 0; i < statuses.size(); i++) {
//			System.out.println(statuses.get(i).getText());
//		}
//
//
//		return (args) -> {
//			// save a few customers
//			System.out.println("In here.");
//		};
//	}

}
