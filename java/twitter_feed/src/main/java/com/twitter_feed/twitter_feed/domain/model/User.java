package com.twitter_feed.twitter_feed.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {

    @ApiModelProperty(value = "Unique identifier per user", example = "Foo")
    private String username;
    @ApiModelProperty(value = "Unique identifier per investment contract to use to create EAC")
    private List<String> usersFollowed;
}
