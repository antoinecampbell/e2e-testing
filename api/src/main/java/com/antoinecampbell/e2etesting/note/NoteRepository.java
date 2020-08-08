package com.antoinecampbell.e2etesting.note;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@RepositoryRestResource
public interface NoteRepository extends CrudRepository<Note, Long> {

    @Query("select n from Note n where n.owner = ?#{principal.username}")
    @Override
    List<Note> findAll();

    @Query("select n from Note n where n.githubRepoUrl = :githubRepoUrl and n.owner = ?#{principal.username}")
    @RestResource(exported = false)
    List<Note> findByGithubRepoUrl(@Param("githubRepoUrl") String githubRepoUrl);

    @Query("delete from Note n where n.id = :id and n.owner = ?#{principal.username}")
    @Modifying
    @Transactional
    @Override
    void deleteById(@Param("id") Long id);
}
