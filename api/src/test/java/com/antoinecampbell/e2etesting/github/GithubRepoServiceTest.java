package com.antoinecampbell.e2etesting.github;

import com.antoinecampbell.e2etesting.TestUtils;
import com.antoinecampbell.e2etesting.note.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
@RunWith(SpringRunner.class)
public class GithubRepoServiceTest {

    private GithubRepoService githubRepoService;
    @MockBean
    private NoteRepository noteRepository;
    @MockBean
    private GithubRepoRestClient githubRepoRestClient;

    @Before
    public void setUp() {
        githubRepoService = new GithubRepoService(githubRepoRestClient, noteRepository);
    }

    @Test
    public void shouldReturnSearchResultsWithoutNotes() {
        when(noteRepository.findByGithubRepoUrl(anyString())).thenReturn(Collections.emptyList());
        when(githubRepoRestClient.search(anyString())).thenReturn(TestUtils.createRepos(0, 2));

        List<GithubRepo> repos = githubRepoService.search("term");
        assertEquals(2, repos.size());
        assertTrue(repos.get(0).getNotes().isEmpty());
        assertTrue(repos.get(1).getNotes().isEmpty());
    }

    @Test
    public void shouldReturnSearchResultsWithNotes() {
        List<GithubRepo> mockRepos = TestUtils.createRepos(0, 2);
        when(githubRepoRestClient.search(anyString())).thenReturn(mockRepos);
        String url = mockRepos.get(0).getUrl();
        when(noteRepository.findByGithubRepoUrl(url))
                .thenReturn(Collections.singletonList(TestUtils.createNote(0, url)));
        url = mockRepos.get(1).getUrl();
        when(noteRepository.findByGithubRepoUrl(url))
                .thenReturn(Collections.singletonList(TestUtils.createNote(1, url)));

        List<GithubRepo> repos = githubRepoService.search("term");
        assertEquals(2, repos.size());
        assertEquals(1, repos.get(0).getNotes().size());
        assertEquals(1, repos.get(1).getNotes().size());
        assertEquals(Long.valueOf(0), repos.get(0).getNotes().get(0).getId());
        assertEquals(Long.valueOf(1), repos.get(1).getNotes().get(0).getId());
    }

    @Test
    public void shouldReturnEmptyListWhenSearchResultsNull() {
        when(githubRepoRestClient.search(anyString())).thenReturn(null);

        List<GithubRepo> repos = githubRepoService.search("term");
        assertEquals(0, repos.size());
        verify(noteRepository, times(0)).findByGithubRepoUrl(anyString());
    }

    @Test
    public void shouldReturnEmptyListWhenSearchResultsEmpty() {
        when(githubRepoRestClient.search(anyString())).thenReturn(Collections.emptyList());

        List<GithubRepo> repos = githubRepoService.search("term");
        assertEquals(0, repos.size());
        verify(noteRepository, times(0)).findByGithubRepoUrl(anyString());
    }

    @Test
    public void shouldReturnRepoWithoutNote() {
        when(githubRepoRestClient.findOne(anyString())).thenReturn(TestUtils.createRepo(0));
        when(noteRepository.findByGithubRepoUrl(anyString())).thenReturn(Collections.emptyList());

        GithubRepo repo = githubRepoService.findOne("url");
        assertNotNull(repo);
        assertEquals(Long.valueOf(0), repo.getId());
        assertTrue(repo.getNotes().isEmpty());
    }

    @Test
    public void shouldReturnRepoWithNote() {
        GithubRepo mockRepo = TestUtils.createRepo(0);
        when(githubRepoRestClient.findOne(anyString())).thenReturn(mockRepo);
        when(noteRepository.findByGithubRepoUrl(anyString()))
                .thenReturn(Collections.singletonList(TestUtils.createNote(0, mockRepo.getUrl())));

        GithubRepo repo = githubRepoService.findOne("url");
        assertNotNull(repo);
        assertEquals(Long.valueOf(0), repo.getId());
        assertEquals(1, repo.getNotes().size());
        assertEquals(Long.valueOf(0), repo.getNotes().get(0).getId());
    }

    @Test
    public void shouldReturnNullRepo() {
        when(githubRepoRestClient.findOne(anyString())).thenReturn(null);

        GithubRepo repo = githubRepoService.findOne("url");
        assertNull(repo);
        verify(noteRepository, times(0)).findByGithubRepoUrl(anyString());
    }
}