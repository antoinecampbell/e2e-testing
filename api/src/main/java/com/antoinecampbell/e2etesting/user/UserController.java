package com.antoinecampbell.e2etesting.user;

import com.antoinecampbell.e2etesting.security.SecurityRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDetailsManager userDetailsManager,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Principal getUser(@AuthenticationPrincipal Principal principal) {
        return principal;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user) {
        if (userDetailsManager.userExists(user.getUsername())) {
            throw new UserNameExistException(user.getUsername());
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                AuthorityUtils.createAuthorityList(SecurityRoles.USER.getRole()));
        userDetailsManager.createUser(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
