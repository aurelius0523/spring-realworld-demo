package com.aurelius.springrealworld.repository;

import com.aurelius.springrealworld.repository.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    @Query("select t from tag t where upper(t.name) = upper(?1)")
    @NonNull
    Optional<TagEntity> findByName(String name);

    Set<TagEntity> findByNameIsInIgnoreCase(Collection<String> names);
}
