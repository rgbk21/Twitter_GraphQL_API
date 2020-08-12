package com.example.twitterAPI;

import com.example.twitterAPI.type.Tweet;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.*;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private Twitter twitter;

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

    public DataFetcher<Object> getTweetsFromUserDataFetcher() {
        return env -> {

            Object userHandleArg = env.getArgument("userHandle");
            String userHandle = (String) userHandleArg;

            Object numOfTweetsToFetchArg = env.getArgument("numOfTweets");
            int numOfTweetsToFetch = Integer.parseInt((String) numOfTweetsToFetchArg) ;

            numOfTweetsToFetch = Math.min(numOfTweetsToFetch, 50);
            int pageSize = 10;

            List<Status> statuses = new ArrayList<>();

            int pageNum = 1;
            while (numOfTweetsToFetch > 0) {

                Paging paging = new Paging(pageNum, Math.min(numOfTweetsToFetch, pageSize));
                paging.setPage(pageNum++);
                statuses.addAll(twitter.getUserTimeline(userHandle, paging));
                numOfTweetsToFetch -= pageSize;

            }

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
}
