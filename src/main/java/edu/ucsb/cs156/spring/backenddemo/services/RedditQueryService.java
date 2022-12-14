package edu.ucsb.cs156.spring.backenddemo.services;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
public class RedditQueryService {

    ObjectMapper mapper = new ObjectMapper();

    private final RestTemplate restTemplate;

    public RedditQueryService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public static final String ENDPOINT = "https://www.reddit.com/r/{subreddit}.json";

    public String getJSON(String subreddit) throws HttpClientErrorException {

        log.info("subreddit={}",subreddit);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("User-Agent","spring-boot:cs156-team04:f22 (by /u/Ok_Dokie_Artichokie)");

        Map<String, String> uriVariables = Map.of("subreddit", subreddit);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> re = restTemplate.exchange(ENDPOINT, HttpMethod.GET, entity, String.class,
                uriVariables);
        return re.getBody();

    }

}