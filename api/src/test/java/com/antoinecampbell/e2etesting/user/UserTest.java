package com.antoinecampbell.e2etesting.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
public class UserTest {

    private User user;
    private Validator validator;

    @Before
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        user = new User();
        user.setUsername("user");
        user.setPassword("user");
    }

    @Test
    public void shouldBeValid() {
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        assertEquals(0, errors.size());
    }

    @Test
    public void shouldBeInvalidUsername() {
        user.setUsername("");
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        assertEquals(1, errors.size());

        user.setUsername("123");
        errors = validator.validate(user);
        assertEquals(1, errors.size());
    }
}