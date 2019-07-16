package com.twitter_feed.twitter_feed.application.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.BASE_URL;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@RequestMapping(BASE_URL)
@ApiResponses(value = {
        @ApiResponse(code = HTTP_OK, message = "OK"),
        @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error")
})
public class FeedsController {

    @PostMapping
    public String uploadContent(@RequestParam("usersFile") MultipartFile usersFile,
                                @RequestParam("feedFile") MultipartFile feedFile) throws IOException {
        return null;
    }
}
