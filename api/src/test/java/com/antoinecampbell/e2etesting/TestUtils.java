package com.antoinecampbell.e2etesting;

import com.antoinecampbell.e2etesting.github.GithubRepo;
import com.antoinecampbell.e2etesting.note.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestUtils {

    public static GithubRepo createRepo(long id) {
        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setId(id);
        githubRepo.setName("Repo " + id);
        githubRepo.setFullName(githubRepo.getName());
        githubRepo.setUrl("https://github.com/" + id);
        githubRepo.setHtmlUrl(githubRepo.getUrl());

        return githubRepo;
    }

    public static List<GithubRepo> createRepos(long idStart, int count) {
        List<GithubRepo> repos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            repos.add(createRepo(i));
        }

        return repos;
    }

    public static Note createNote(long id, String githubRepoUrl) {
        Note note = new Note();
        note.setId(id);
        note.setTitle("Note " + id);
        note.setDescription(note.getTitle());
        note.setGithubRepoUrl(githubRepoUrl);

        return note;
    }
}
