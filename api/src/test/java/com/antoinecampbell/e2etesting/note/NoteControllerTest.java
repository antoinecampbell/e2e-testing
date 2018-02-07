package com.antoinecampbell.e2etesting.note;

import com.antoinecampbell.e2etesting.TestUtils;
import com.antoinecampbell.e2etesting.github.GithubRepoRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GithubRepoRestClient githubRepoRestClient;
    private Note note;

    @Before
    public void setUp() {
        note = TestUtils.createNote(0, "url");
        note.setId(null);
        note = testEntityManager.persistAndFlush(note);

        when(githubRepoRestClient.findOne(anyString()))
                .thenReturn(TestUtils.createRepo(0));
    }

    @Test
    public void shouldCreateNote() throws Exception {
        Note note = TestUtils.createNote(0, "url");

        mockMvc.perform(post("/notes")
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNoteWithRepo() throws Exception {
        mockMvc.perform(get("/notes/{id}", note.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(note.getTitle())))
                .andExpect(jsonPath("$.description", is(note.getDescription())))
                .andExpect(jsonPath("$.githubRepo").isNotEmpty())
                .andExpect(jsonPath("$.githubRepo.id", is(0)));
    }
}
