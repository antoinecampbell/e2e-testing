package com.antoinecampbell.e2etesting.github;

import org.springframework.web.client.RestClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 */
public interface GithubRepoRestClient {

    List<GithubRepo> search(String query);

    GithubRepo findOne(String repoUrl);

    default String validateUrl(String repoUrl) {
        try {
            URL url = new URL(repoUrl);
            if (!url.getHost().contains("github.com")) {
                throw new RestClientException("Invalid Github repo URL");
            }
        } catch (MalformedURLException e) {
            throw new RestClientException("Invalid URL", e);
        }
        return repoUrl;
    }
}
