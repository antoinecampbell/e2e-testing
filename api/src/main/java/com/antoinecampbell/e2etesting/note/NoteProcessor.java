package com.antoinecampbell.e2etesting.note;

import com.antoinecampbell.e2etesting.github.GithubRepoRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;


/**
 *
 */
@Component
public class NoteProcessor implements RepresentationModelProcessor<EntityModel<Note>> {

    private final GithubRepoRestClient githubRepoRestClient;

    @Autowired
    public NoteProcessor(GithubRepoRestClient githubRepoRestClient) {
        this.githubRepoRestClient = githubRepoRestClient;
    }

    @Override
    public EntityModel<Note> process(EntityModel<Note> entityModel) {
        Note note = entityModel.getContent();
        note.setGithubRepo(githubRepoRestClient.findOne(note.getGithubRepoUrl()));

        return entityModel;
    }
}
