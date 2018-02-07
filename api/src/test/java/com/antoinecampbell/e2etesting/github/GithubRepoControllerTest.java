package com.antoinecampbell.e2etesting.github;

import com.antoinecampbell.e2etesting.TestUtils;
import com.antoinecampbell.e2etesting.note.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
@Transactional
@WithMockUser
public class GithubRepoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;
    @MockBean
    private GithubRepoRestClient githubRepoRestClient;
    private Note note;


    @Before
    public void setUp() {
        note = TestUtils.createNote(0, "url");
        note.setId(null);
        note = testEntityManager.persistAndFlush(note);

        GithubRepo repo = TestUtils.createRepo(0);
        repo.setUrl(note.getGithubRepoUrl());

        when(githubRepoRestClient.findOne(anyString()))
                .thenReturn(repo);
        when(githubRepoRestClient.search(anyString()))
                .thenReturn(Collections.singletonList(repo));
    }

    @Test
    public void shouldReturnRepoWith1Note() throws Exception {
        mockMvc.perform(get("/repos")
                .param("url", note.getGithubRepoUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").isArray())
                .andExpect(jsonPath("$.notes.size()", is(1)));
    }

    @Test
    public void shouldReturnRepoWith2Notes() throws Exception {
        insertNote();

        mockMvc.perform(get("/repos")
                .param("url", note.getGithubRepoUrl()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").isArray())
                .andExpect(jsonPath("$.notes.size()", is(2)));
    }

    @Test
    public void shouldSearchReposWith1Note() throws Exception {
        mockMvc.perform(get("/repos")
                .param("q", "term"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.githubRepos").isArray())
                .andExpect(jsonPath("$._embedded.githubRepos.size()", is(1)))
                .andExpect(jsonPath("$._embedded.githubRepos[0].notes").isArray())
                .andExpect(jsonPath("$._embedded.githubRepos[0].notes.size()", is(1)));
    }

    @Test
    public void shouldSearchReposWith2Notes() throws Exception {
        insertNote();

        mockMvc.perform(get("/repos")
                .param("q", "term"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.githubRepos").isArray())
                .andExpect(jsonPath("$._embedded.githubRepos.size()", is(1)))
                .andExpect(jsonPath("$._embedded.githubRepos[0].notes").isArray())
                .andExpect(jsonPath("$._embedded.githubRepos[0].notes.size()", is(2)));
    }

    private void insertNote() {
        Note note = TestUtils.createNote(0, "url");
        note.setId(null);
        testEntityManager.persistAndFlush(note);
    }
}