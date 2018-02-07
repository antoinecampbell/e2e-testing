package com.antoinecampbell.e2etesting.github;

import com.antoinecampbell.e2etesting.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@RestClientTest(GithubRepoRestClientImpl.class)
@AutoConfigureMockRestServiceServer
public class GithubRepoRestClientImplTest {

    @Autowired
    private GithubRepoRestClient githubRepoRestClient;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldReturnSearchResults() throws JsonProcessingException {
        GithubRepoSearchResponse response = new GithubRepoSearchResponse();
        response.setItems(TestUtils.createRepos(0, 2));
        mockRestServiceServer.expect(requestTo(startsWith("/search/repositories")))
                .andExpect(queryParam("sort", "stars"))
                .andExpect(queryParam("q", "term"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(response), MediaType.APPLICATION_JSON));

        List<GithubRepo> repos = githubRepoRestClient.search("term");
        assertEquals(2, repos.size());
    }

    @Test(expected = RestClientException.class)
    public void shouldThrowErrorWhenNotSuccessBadRequest() {
        mockRestServiceServer.expect(requestTo(startsWith("/search/repositories")))
                .andRespond(withBadRequest());

        githubRepoRestClient.search("term");
    }

    @Test(expected = RestClientException.class)
    public void shouldThrowErrorWhenNotSuccessServerError() {
        mockRestServiceServer.expect(requestTo(startsWith("/search/repositories")))
                .andRespond(withServerError());

        githubRepoRestClient.search("term");
    }

    @Test
    public void shouldReturnRepo() throws JsonProcessingException {
        GithubRepo mockRepo = TestUtils.createRepo(0);
        mockRestServiceServer.expect(requestTo(startsWith("/123")))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockRepo), MediaType.APPLICATION_JSON));

        GithubRepo repo = githubRepoRestClient.findOne("/123");
        assertNotNull(repo);
        assertEquals(Long.valueOf(0), repo.getId());
    }

    @Test
    public void shouldReturnNullRepo() throws JsonProcessingException {
        mockRestServiceServer.expect(requestTo(startsWith("/123")))
                .andRespond(withSuccess(objectMapper.writeValueAsString(null), MediaType.APPLICATION_JSON));

        GithubRepo repo = githubRepoRestClient.findOne("/123");
        assertNull(repo);
    }

    @Test(expected = RestClientException.class)
    public void shouldReturnThrowExceptionForBadRequest() {
        mockRestServiceServer.expect(requestTo(startsWith("/123")))
                .andRespond(withBadRequest());

        githubRepoRestClient.findOne("/123");
    }

    @Test(expected = RestClientException.class)
    public void shouldReturnThrowExceptionForServerError() {
        mockRestServiceServer.expect(requestTo(startsWith("/123")))
                .andRespond(withServerError());

        githubRepoRestClient.findOne("/123");
    }
}