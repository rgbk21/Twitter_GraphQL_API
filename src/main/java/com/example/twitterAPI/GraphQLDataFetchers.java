package com.example.twitterAPI;

import com.example.twitterAPI.type.Tweet;
import com.google.common.collect.ImmutableMap;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.*;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private Twitter twitter;

    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public DataFetcher getTweetByIdDataFetcher() {
        return env -> {
            Object rawTweetIds = env.getArgument("tweetId");
            List<String> tweetIds = (List<String>) rawTweetIds;
            String tweetId;
            if (tweetIds != null && !tweetIds.isEmpty()) tweetId = tweetIds.get(0);


            //This example is from user-context
            Map<String, Tweet> someMap = new HashMap<>();
            return DataFetcherResult.newResult().data(tweetIds).localContext(someMap).build();

        };
    }

    public DataFetcher getTweetsFromUserDataFetcher() {
        return env -> {

            Object userHandleArg = env.getArgument("userHandle");
            String userHandle = (String) userHandleArg;

            Object numOfTweetsToFetchArg = env.getArgument("numOfTweets");
            int numOfTweetsToFetch = Integer.parseInt((String)numOfTweetsToFetchArg) ;

            List<Status> statuses = new ArrayList<>();
            Paging page = new Paging(1, 10);
            page.setPage(1);
            statuses.addAll(twitter.getUserTimeline(userHandle, page));

            List<Tweet> allUserTweets = new ArrayList<>();
            for (Status status : statuses) {

                Tweet tweet = new Tweet();
                tweet.setId(Long.toString(status.getId()));
                tweet.setAuthor_id(status.getUser().getName());
                tweet.setCreated_at(status.getCreatedAt().toString());
                tweet.setIn_reply_to_user_id(status.getInReplyToScreenName());
                tweet.setText(status.getText());

                allUserTweets.add(tweet);
            }

            //This example is from user-context
            return DataFetcherResult.newResult().data(allUserTweets).build();

        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
}
