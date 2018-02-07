package com.antoinecampbell.e2etesting.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(method = RequestMethod.GET, params = "q")
    public Resources<Resource<GithubRepo>> search(@RequestParam("q") String query) {
        return Resources.wrap(githubRepoService.search(query));
    }

    @RequestMapping(method = RequestMethod.GET, params = "url")
    public GithubRepo findOne(@RequestParam("url") String url) {
        return githubRepoService.findOne(url);
    }
}
