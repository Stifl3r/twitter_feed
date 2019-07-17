package com.twitter_feed.twitter_feed.application.controller;

import com.twitter_feed.twitter_feed.domain.service.FeedService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.twitter_feed.twitter_feed.application.controller.constant.RestConstants.BASE_URL;
import static java.net.HttpURLConnection.*;

@RestController
@RequestMapping(BASE_URL)
@ApiResponses(value = {
        @ApiResponse(code = HTTP_OK, message = "OK"),
        @ApiResponse(code = HTTP_BAD_REQUEST, message = "Bad Request"),
        @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Internal server error")
})
public class FeedsController {

    @Autowired
    private FeedService feedService;

    @PostMapping("")
    public ResponseEntity<?> uploadContent(@RequestParam("tweetFile") MultipartFile tweetFile,
                                               @RequestParam("usersFile") MultipartFile usersFile) throws IOException {
        try {
            if (isFileTypeValid(usersFile) && isFileTypeValid(usersFile)) {
                String result = feedService.process(tweetFile, usersFile);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest()
                        .body("Only Text files are allowed");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return ResponseEntity.badRequest()
                    .body("File size limit exceeded");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Oops! Something went wrong, Please contact administrator.");
        }
    }

    private boolean isFileTypeValid(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] splitName = filename.split("\\.");
        if (splitName.length > 2 ||
        !splitName[1].equals("txt")) {
            return false;
        }
        return true;
    }
}
