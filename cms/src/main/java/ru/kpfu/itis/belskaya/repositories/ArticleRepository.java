package ru.kpfu.itis.belskaya.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.belskaya.models.Article;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    Optional<Article> findBySlug(String slug);

    Optional<Article> findByTitle(String title);

    @Transactional
    void removeArticleBySlug(String slug);
}