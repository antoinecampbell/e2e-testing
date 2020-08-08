package com.antoinecampbell.e2etesting.note;

import com.antoinecampbell.e2etesting.github.GithubRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 */
@Entity
@Table(name = "notes")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_repo_url", length = 2000, nullable = false)
    private String githubRepoUrl;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @CreatedBy
    @JsonIgnore
    @Column(name = "owner", length = 50, nullable = false)
    private String owner;

    @Transient
    private GithubRepo githubRepo;
}
