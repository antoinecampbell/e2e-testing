package com.antoinecampbell.e2etesting.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 *
 */
@RestController
@RequestMapping("/repos")
@Validated
public class GithubRepoController {

    private final GithubRepoService githubRepoService;

    @Autowired
    public GithubRepoController(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @GetMapping(params = "q")
    public CollectionModel<EntityModel<GithubRepo>> search(@RequestParam("q") String query) {
        return CollectionModel.wrap(githubRepoService.search(query));
    }

    @GetMapping(params = "url")
    public GithubRepo findOne(@RequestParam("url") @NotBlank String url) {
        return githubRepoService.findOne(url);
    }
}
