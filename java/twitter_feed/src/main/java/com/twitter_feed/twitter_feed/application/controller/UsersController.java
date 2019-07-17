package com.twitter_feed.twitter_feed.application.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.BASE_URL;
import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.USERS_URL;

@Api(tags = "Users")
@RestController
@RequestMapping(BASE_URL + USERS_URL)
public class UsersController {

    @GetMapping
    public ResponseEntity getUsers() {
        return null;
    }
}
