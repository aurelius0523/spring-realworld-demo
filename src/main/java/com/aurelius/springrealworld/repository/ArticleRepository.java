package com.aurelius.springrealworld.repository;

import com.aurelius.springrealworld.repository.entities.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, QuerydslPredicateExecutor {
    @Query("select distinct a from article a " +
            "left join a.tagEntitySet tagEntitySet where " +
            "(:username is null or upper(a.author.username) = upper(:username)) or " +
            "(:tag is null or tagEntitySet.name = :tag)")
    Page<ArticleEntity> filterSome(@Param("username") @Nullable String username, @Param("tag") @Nullable String tag, Pageable pageable);
}
