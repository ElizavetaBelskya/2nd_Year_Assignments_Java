package ru.kpfu.itis.belskaya.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.belskaya.models.Article;
import ru.kpfu.itis.belskaya.models.forms.ArticleUpdateDto;
import ru.kpfu.itis.belskaya.repositories.ArticleRepository;

import java.util.Optional;

@Controller
@RestController
@RequestMapping(value = "/api/article")
public class ArticleRestController {

    @Autowired
    private ArticleRepository articleRepository;


    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        if (articleRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(articleRepository.findById(id).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        article.ifPresent(value -> articleRepository.delete(value));
        return ResponseEntity.accepted().build();
    }


    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, ArticleUpdateDto updatedArticle) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setTitle(updatedArticle.getTitle());
            article.get().setText(updatedArticle.getText());
            articleRepository.save(article.get());
        }
        return article.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Article> addArticle(ArticleUpdateDto updatedArticle) {
        Article article = Article.builder().title(updatedArticle.getTitle()).text(updatedArticle.getText()).build();
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
