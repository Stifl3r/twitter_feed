package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.Tweet;
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


        List<User> users = processUsers(usersFile);

        List<Tweet> tweets = processTweets(feedFile);

        processFeed(users, tweets);
        return null;
    }

    private void processFeed(List<User> users, List<Tweet> tweets) {
        for(User user: users) {
            System.out.println(user.getUsername());
            for(Tweet tweet: tweets) {
                if(tweet.getUsername().equals(user.getUsername()) ||
                  user.getUsersFollowed().contains(tweet.getUsername())) {
                    System.out.println("\t\t@"+tweet.getUsername() + ": " +tweet.getMessage());
                }
            }
        }
    }

    private List<Tweet> processTweets(MultipartFile feedFile) throws IOException {
        String content = new String(feedFile.getBytes());
        String[] lines = content.split("\\r?\\n");

        List<Tweet> result = new ArrayList<>();

        for(String line: lines) {
            String[] lineSplit = line.split(">");
            Tweet tweet = new Tweet();
            tweet.setUsername(lineSplit[0]);
            tweet.setMessage(lineSplit[1]);
            result.add(tweet);
        }
        return result;
    }

    private List<User> processUsers(MultipartFile usersFile) throws IOException {
        String content = new String(usersFile.getBytes());
        String[] lines = content.split("\\r?\\n");


        List<User> result = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        for (String line: lines) {

            String[] lineSplit = line.split(" ");

            List<String> lineList = new ArrayList<>(lineSplit.length);
            Collections.addAll(lineList, lineSplit);
            String userToBeAdded = (lineSplit[0]);
            lineList.remove(0);
            lineList.remove(0);

            if (users.contains(userToBeAdded)) {
                User currentUser = result.stream()
                        .filter(user -> userToBeAdded.equals(user.getUsername()))
                        .findFirst()
                        .orElse(null);

                result.removeIf(user -> userToBeAdded.equals(user.getUsername()));



                for(String follower : lineList) {
                   if(currentUser.getUsersFollowed().contains(follower)) {

                   } else {
                       currentUser.getUsersFollowed().add(follower);
                   }
                }

                result.add(currentUser);


                System.out.println(" ");

            } else {
                users.add(userToBeAdded);

                User user = new User();
                user.setUsername(lineSplit[0]);
                user.setUsersFollowed(lineList);

                result.add(user);
            }
            users.add(userToBeAdded);

        }
        return result;
    }
}
