package com.twitter_feed.twitter_feed.application.controller;

import com.twitter_feed.twitter_feed.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.BASE_URL;
import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.USERS_URL;
import static java.net.HttpURLConnection.*;

@Api(tags = "Users")
@RestController
@RequestMapping(BASE_URL + USERS_URL)
@ApiResponses(value = {
        @ApiResponse(code = HTTP_OK, message = "OK"),
        @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error")
})
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getUsers() throws IOException {
        return ResponseEntity.ok(userService.getListOfUsers());
    }
}
