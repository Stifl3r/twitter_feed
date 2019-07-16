package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.Tweet;
import com.twitter_feed.twitter_feed.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeedService.class)
@ActiveProfiles("local")
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Test
    public void test() throws IOException {
        MockMultipartFile userFile = new MockMultipartFile("data", "user.txt", "text/plain", "Alan follows Martin".getBytes());
        MockMultipartFile feedFile = new MockMultipartFile("data", "tweet.txt", "text/plain", "Alan> Random numbers should not be generated with a method chosen at random.\n".getBytes());

        String result = feedService.process(feedFile, userFile);

        assertThat(result).isEqualTo("Success");
    }

    @Test
    public void processUsersTest() throws IOException {

        MockMultipartFile userFile = new MockMultipartFile("data", "user.txt", "text/plain", "Alan follows Martin".getBytes());

        List<User> result = feedService.processUsers(userFile);

        assertThat(result.get(0).getUsername()).isEqualTo("Alan");

    }

    @Test
    public void processTweetsTest() throws IOException {
        MockMultipartFile tweetsFile = new MockMultipartFile("data", "tweet.txt", "text/plain", "Martin> Random numbers should not be generated with a method chosen at random.\n".getBytes());
        Tweet tweet = new Tweet();
        tweet.setUsername("Martin");
        tweet.setMessage(" Random numbers should not be generated with a method chosen at random.");

        List<Tweet> result = feedService.processTweets(tweetsFile);

        assertThat(result.get(0)).isEqualToComparingFieldByFieldRecursively(tweet);
    }
}
