package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.User;
import org.springframework.stereotype.Component;
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
            String userToBeAdded = (lineSplit[0]);
            if (users.contains(userToBeAdded)) {
                User currentUser = result.stream()
                        .filter(user -> userToBeAdded.equals(user.getUsername()))
                        .findFirst()
                        .orElse(null);

                result.removeIf(user -> userToBeAdded.equals(user.getUsername()));

                lineList.remove(0);
                lineList.remove(0);

                for(String follower : lineList) {
                   if(currentUser.getUsersFollowed().contains(follower)) {

                   } else {
                       currentUser.getUsersFollowed().add(follower);
                   }
                }

                result.add(currentUser);


                System.out.println(" ");

            } else {
                users.add(lineSplit[0]);

                User user = new User();
                user.setUsername(lineSplit[0]);
                lineList.remove(0);
                lineList.remove(0);

                user.setUsersFollowed(lineList);

                result.add(user);
            }

        }
        return result;
    }
}
