# twitter_feed
Simulation of a twitter feed app

## Dependencies:

Java 1.8 <=

Gradle

Springboot 2

## Instructions for the app
1. ```git clone https://github.com/Stifl3r/twitter_feed.git```
 or download zipfolder

2. Open terminal

3. cd to twitter_feed/java/twitter_feed

4. run ```gradle build``` then ```gradle bootrun```

(Known issues with annotation processing when running the app in terminal. Enabling it in IDE IntellJ preferences> build, execution deployment> annotation processing > enable... fixes the issue. Working on a fix)

5. Open browser and navigate to http://localhost:8080/swagger-ui.html#/

You should now be able to upload feedfile and tweetfile under the feedController on swagger and execute.

Console output will be on the terminal the project is run from

## Assumptions 
Based on the user sample files provided,an assumption has been made that users being followed will always be delimeted by ',' and ' '. Eg: 'User follows foo, bar, pie, roasted'.

## Stretch Goals:

1. Include crud operations for user and tweets
2. Introduce a formal storage(mongo or postgres)
3. Create SPA(Angular or React) to consume api
4. run spa, api and storage in docker containers

