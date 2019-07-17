package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@Slf4j
public class UserService {

    @Autowired
    private FeedService feedService;

    public List<User> getListOfUsers() throws IOException {
        return feedService.processUsers(getRawData());
    }

    public User getUserByUsername(String username) throws IOException {
        List<User> users = feedService.processUsers(getRawData());
        return users.stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    }

    private String getRawData() throws IOException {
        File file = getFileFromResources("user.txt");
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
