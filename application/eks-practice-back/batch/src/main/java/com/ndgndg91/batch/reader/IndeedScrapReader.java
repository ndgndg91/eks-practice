package com.ndgndg91.batch.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class IndeedScrapReader implements ItemReader<String> {
    private static final int MAX_PAGE = 19;
    private int currentPage = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("돌고 있는지 모르겠네?");
        if (currentPage > MAX_PAGE) {
            return null;
        }

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest get = HttpRequest.newBuilder(URI.create("https://kr.indeed.com/%EC%B7%A8%EC%97%85?q=java&limit=50&start=" + currentPage * 50)).GET().build();
        HttpResponse<String> response = client.send(get, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            log.info("Indeed Page Response Code : {}", response.statusCode());
            log.info("Indeed Page Response Body : {}", response.body());
        }

        currentPage++;
        return response.body();
    }
}
