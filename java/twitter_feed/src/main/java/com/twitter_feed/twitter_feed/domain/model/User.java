package com.twitter_feed.twitter_feed.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private String username;
    private List<String> usersFollowed;
}
