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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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

        List<User> result = feedService.processUsers(getRawData("user.txt"));

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

    private String getRawData(String fileName) throws IOException {
        File file = getFileFromResources(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while((line = br.readLine())!=null) {

            stringBuffer.append(line).append("\n");
        }
        return stringBuffer.toString();
    }

    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
