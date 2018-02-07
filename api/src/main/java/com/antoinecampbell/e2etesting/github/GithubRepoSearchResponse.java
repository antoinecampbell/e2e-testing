package com.antoinecampbell.e2etesting.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class GithubRepoSearchResponse {

    @JsonProperty("total_count")
    private Integer totalCount;

    private List<GithubRepo> items;
}
