package com.twitter_feed.twitter_feed.domain.service;

import com.twitter_feed.twitter_feed.domain.model.Tweet;
import com.twitter_feed.twitter_feed.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class FeedService {

    public String process(MultipartFile feedFile, MultipartFile usersFile) throws IOException {

        List<User> users = processUsers(new String(usersFile.getBytes()));

        users.sort(Comparator.comparing(o -> o.getUsername()));

        List<Tweet> tweets = processTweets(feedFile);

        processFeed(users, tweets);
        return "Success";
    }

    private void processFeed(List<User> users, List<Tweet> tweets) {
        log.info("Process timeline feed from a list of users and tweets");

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

    protected List<Tweet> processTweets(MultipartFile feedFile) throws IOException {
        log.info("Read multipart file and arrange tweets per user");

        // Read file and format tweets per user
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

    protected List<User> processUsers(String content) throws IOException {
        log.info("Read multipart file and arrange users and users followed");
        // Read file contents and split into lines
        String[] lines = content.split("\\r?\\n");

        List<User> result = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        for (String line: lines) {

            // Delimit lines and add users
            String[] lineSplit = line.split(" ");

            List<String> lineList = new ArrayList<>(lineSplit.length);
            Collections.addAll(lineList, lineSplit);
            String userToBeAdded = (lineSplit[0]);
            lineList.remove(0);
            lineList.remove(0);

            List<String> followingList = new ArrayList<>();
            for(String item: lineList) {
               followingList.add(item.replace("," , ""));
            }

            // Retrieve existing user and add users followed
            if (users.contains(userToBeAdded)) {
                User currentUser = result.stream()
                        .filter(user -> userToBeAdded.equals(user.getUsername()))
                        .findFirst()
                        .orElse(null);

                result.removeIf(user -> userToBeAdded.equals(user.getUsername()));

                List<String> newFollowingList = null;
                if (currentUser != null) {
                    newFollowingList = new ArrayList<>(currentUser.getUsersFollowed());
                }
                for(String follower : followingList) {
                    if (currentUser.getUsersFollowed() == null || (!currentUser.getUsersFollowed().contains(follower))) {
                        newFollowingList.add(follower);
                    }
                }
                currentUser.setUsersFollowed(newFollowingList);
                result.add(currentUser);

            } else {
                users.add(userToBeAdded);

                User user = new User();
                user.setUsername(lineSplit[0]);
                user.setUsersFollowed(followingList);

                result.add(user);
            }
            users.add(userToBeAdded);
            // Add users that are followed but not provided in the file
            for(String item: followingList) {
                if(!users.contains(item)) {
                    User newUser = new User();
                    newUser.setUsername(item);
                    newUser.setUsersFollowed(Collections.EMPTY_LIST);
                    result.add(newUser);
                    users.add(item);
                }
            }
        }
        return result;
    }
}
