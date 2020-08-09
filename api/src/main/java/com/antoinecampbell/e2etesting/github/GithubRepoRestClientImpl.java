package com.antoinecampbell.e2etesting.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Profile("default")
@Component
public class GithubRepoRestClientImpl implements GithubRepoRestClient {

    private final RestTemplate restTemplate;
    private final String searchUri;


    @Autowired
    public GithubRepoRestClientImpl(RestTemplateBuilder restTemplateBuilder,
                                    @Value("${rest.github.github.uri:https://api.github.com}") String uri,
                                    @Value("${rest.github.github.repo.search:/search/repositories}") String searchUri) {
        this.searchUri = searchUri;
        this.restTemplate = restTemplateBuilder.rootUri(uri).build();
    }

    @Cacheable("githubRepoSearch")
    @Override
    public List<GithubRepo> search(String query) {
        String path = UriComponentsBuilder.fromPath(searchUri)
                .queryParam("q", query)
                .queryParam("sort", "stars")
                .build().toUri().toString();
        ResponseEntity<GithubRepoSearchResponse> response = restTemplate.getForEntity(path, GithubRepoSearchResponse.class);
        return Optional.ofNullable(response.getBody().getItems()).orElse(Collections.emptyList());
    }

    @Cacheable("githubRepo")
    @Override
    public GithubRepo findOne(String repoUrl) {
        String url = validateUrl(repoUrl);
        return restTemplate.getForEntity(url, GithubRepo.class).getBody();
    }
}
