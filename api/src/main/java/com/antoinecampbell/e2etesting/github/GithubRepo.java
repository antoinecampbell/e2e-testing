package com.antoinecampbell.e2etesting.github;

import com.antoinecampbell.e2etesting.note.Note;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Transient;
import java.util.List;

/**
 *
 */
@Data
@Relation(collectionRelation = "githubRepos")
public class GithubRepo {

    private Long id;
    private String url;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String description;
    @JsonProperty("html_url")
    private String htmlUrl;
    private Long watchers;
    private Long forks;
    @JsonProperty("stargazers_count")
    private Long stars;
    private Owner owner;

    @Transient
    private List<Note> notes;

    @Data
    private static class Owner {
        @JsonProperty("avatar_url")
        private String avatarUrl;
    }
}
