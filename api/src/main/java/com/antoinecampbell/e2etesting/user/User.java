package com.antoinecampbell.e2etesting.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 */
@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "username", nullable = false, length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    @NotNull
    @Size(min = 4, max = 500)
    private String password;

    @Column(name = "enabled", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled = Boolean.TRUE;

}
