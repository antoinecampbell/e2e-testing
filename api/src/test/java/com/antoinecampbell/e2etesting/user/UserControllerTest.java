package com.antoinecampbell.e2etesting.user;

import com.antoinecampbell.e2etesting.security.SecurityRoles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                "test",
                passwordEncoder.encode("test"),
                Collections.singletonList(new SimpleGrantedAuthority(SecurityRoles.USER.getRole()))));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldConflictCreatingSameUser() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    public void shouldAllowLoginWithUsernameAndPassword() throws Exception {
        mockMvc.perform(get("/users")
                .with(httpBasic("test", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test")));
    }

    @Test
    public void shouldDisallowLoginWithUsernameAndWrongPassword() throws Exception {
        mockMvc.perform(get("/users")
                .with(httpBasic("test", "")))
                .andExpect(status().isUnauthorized());
    }
}