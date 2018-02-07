package com.antoinecampbell.e2etesting.github;

import com.antoinecampbell.e2etesting.note.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class GithubRepoService {

    private final GithubRepoRestClient githubRepoRestClient;
    private final NoteRepository noteRepository;

    public GithubRepoService(GithubRepoRestClient githubRepoRestClient, NoteRepository noteRepository) {
        this.githubRepoRestClient = githubRepoRestClient;
        this.noteRepository = noteRepository;
    }

    public List<GithubRepo> search(String query) {
        List<GithubRepo> repos = Optional.ofNullable(githubRepoRestClient.search(query)).orElse(Collections.emptyList());
        repos.forEach(githubRepo -> githubRepo.setNotes(noteRepository.findByGithubRepoUrl(githubRepo.getUrl())));

        return repos;
    }

    public GithubRepo findOne(String url) {
        Optional<GithubRepo> repo = Optional.ofNullable(githubRepoRestClient.findOne(url));
        repo.ifPresent(githubRepo -> githubRepo.setNotes(noteRepository.findByGithubRepoUrl(githubRepo.getUrl())));

        return repo.orElse(null);
    }
}
