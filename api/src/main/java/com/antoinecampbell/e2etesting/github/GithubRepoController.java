package com.antoinecampbell.e2etesting.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 */
@RestController
@RequestMapping("/repos")
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
    public GithubRepo findOne(@RequestParam("url") String url) {
        return githubRepoService.findOne(url);
    }
}
