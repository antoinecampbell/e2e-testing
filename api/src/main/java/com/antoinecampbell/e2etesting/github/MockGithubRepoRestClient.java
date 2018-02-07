package com.antoinecampbell.e2etesting.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.List;

/**
 *
 */
@Profile("e2e")
@Component
public class MockGithubRepoRestClient implements GithubRepoRestClient {

    private final ObjectMapper objectMapper;

    @Autowired
    public MockGithubRepoRestClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<GithubRepo> search(String query) {
        return loadRepos();
    }

    @Override
    public GithubRepo findOne(String url) {
        List<GithubRepo> githubRepos = loadRepos();
        GithubRepo githubRepo = null;
        for (GithubRepo repo : githubRepos) {
            if (ObjectUtils.nullSafeEquals(url, repo.getUrl())) {
                githubRepo = repo;
                break;
            }
        }
        return githubRepo;
    }

    private List<GithubRepo> loadRepos() {
        try {
            GithubRepoSearchResponse githubRepoSearchResponse =
                    objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("test.json"), GithubRepoSearchResponse.class);
            return githubRepoSearchResponse.getItems();
        } catch (IOException e) {
            throw new RestClientException("Error loading mock data", e);
        }
    }
}
