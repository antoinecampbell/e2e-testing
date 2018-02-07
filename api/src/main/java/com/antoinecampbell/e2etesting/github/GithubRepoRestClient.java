package com.antoinecampbell.e2etesting.github;

import java.util.List;

/**
 *
 */
public interface GithubRepoRestClient {

    List<GithubRepo> search(String query);

    GithubRepo findOne(String url);
}
