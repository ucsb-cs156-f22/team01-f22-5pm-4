package edu.ucsb.cs156.spring.backenddemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

@RestClientTest(RedditQueryService.class)
public class RedditQueryServiceTests {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private  RedditQueryService RedditQueryService;

    @Test
    public void test_getJSON() {

        String subreddit = "mySubreddit";

        String expectedURL = edu.ucsb.cs156.spring.backenddemo.services.RedditQueryService.ENDPOINT.replace("{subreddit}", subreddit);;
        String fakeJsonResult = "{ \"fake\" : \"result\" }";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("User-Agent","spring-boot:cs156-team04:f22 (by /u/Ok_Dokie_Artichokie)"))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = RedditQueryService.getJSON(subreddit);
        assertEquals(fakeJsonResult, actualResult);
    }
}