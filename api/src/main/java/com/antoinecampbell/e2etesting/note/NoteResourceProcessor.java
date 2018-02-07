package com.antoinecampbell.e2etesting.note;

import com.antoinecampbell.e2etesting.github.GithubRepoRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class NoteResourceProcessor implements ResourceProcessor<Resource<Note>> {

    private final GithubRepoRestClient githubRepoRestClient;

    @Autowired
    public NoteResourceProcessor(GithubRepoRestClient githubRepoRestClient) {
        this.githubRepoRestClient = githubRepoRestClient;
    }

    @Override
    public Resource<Note> process(Resource<Note> resource) {
        Note note = resource.getContent();
        note.setGithubRepo(githubRepoRestClient.findOne(note.getGithubRepoUrl()));

        return resource;
    }
}
