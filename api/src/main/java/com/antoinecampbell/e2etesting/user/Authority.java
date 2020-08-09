package com.antoinecampbell.e2etesting.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 *
 */
@Entity
@Data
@Table(name = "authorities",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "authority"})})
@IdClass(Authority.Key.class)
public class Authority {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Id
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @Data
    public static class Key implements Serializable {
        private User user;
        private String authority;
    }
}
