package com.antoinecampbell.e2etesting.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


/**
 *
 */
@RestController
@RequestMapping("/repos")
@Validated
public class GithubRepoController implements RepresentationModelProcessor<RepositoryLinksResource> {

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

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        return model.add(WebMvcLinkBuilder
                .linkTo(GithubRepoController.class)
                .withRel("repos"));
    }
}
