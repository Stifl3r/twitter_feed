package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FeedService {

    public String process(MultipartFile feedFile, MultipartFile usersFile) throws IOException {
        String content = new String(usersFile.getBytes());
        String[] lines = content.split("\\r?\\n");

        List<User> users = processUsers(lines);
        return null;
    }

    private List<User> processUsers(String[] lines) {

        List<User> result = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        for (String line: lines) {

            String[] lineSplit = line.split(" ");

            List<String> lineList = new ArrayList<>(lineSplit.length);
            Collections.addAll(lineList, lineSplit);
            users.add( (line.split("\\ ?\\,"))[0]);

            User user = new User();
            user.setUsername(lineSplit[0]);
            lineList.remove(0);
            lineList.remove(0);

            user.setUsersFollowed(lineList);

            result.add(user);

        }
        return result;
    }
}
