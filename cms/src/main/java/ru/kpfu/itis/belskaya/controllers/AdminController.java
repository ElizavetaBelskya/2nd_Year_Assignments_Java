package ru.kpfu.itis.belskaya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.belskaya.helpers.HtmlFilter;
import ru.kpfu.itis.belskaya.models.Article;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.repositories.ArticleRepository;
import ru.kpfu.itis.belskaya.services.UserService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;

    private static final int PAGE_SIZE = 3;


    @RequestMapping(value = "articles", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String list(@RequestParam(defaultValue = "1") Integer page, ModelMap map) {
        if (page == null || page <= 0) {
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
        }
        long totalPages = articleRepository.count() / PAGE_SIZE + (articleRepository.count() % PAGE_SIZE != 0 ? 1 : 0);

        if (page > totalPages) {
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, (int) totalPages).build();
        }
        Pageable pageable = PageRequest.of(page-1, PAGE_SIZE);
        Page<Article> articles = articleRepository.findAll(pageable);
        map.put("articles", articles);
        map.put("page", page);
        map.put("totalPages", totalPages);
        return "articles";
    }

//    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
//    public String article(@PathVariable Long id, ModelMap map) {
//        Optional<Article> article = articleRepository.findById(id);
//        if (!article.isPresent()) {
//            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
//        } else {
//            map.put("article", article.get());
//            return "article";
//        }
//    }

    @RequestMapping(value = "/delete/{slug}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteArticle(@PathVariable String slug) {
        articleRepository.removeArticleBySlug(slug);
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
    }

    @RequestMapping(value = "/new_article", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String creator(ModelMap map) {
        map.put("article", new Article());
        return "article_creator";
    }

    @RequestMapping(value = "/new_article", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createNewArticle(RedirectAttributes redirectAttributes,
                                   @Valid @ModelAttribute("article") Article article,
                                   BindingResult result,
                                   ModelMap map) {
        if (result.hasErrors()) {
            return "article_editor";
        } else {
            String text = HtmlFilter.filterHtml(article.getText());
            article.setText(text);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            article.setAuthor(user);
            Optional<Article> sameTitleArticle = articleRepository.findByTitle(article.getTitle());
            if (sameTitleArticle.isPresent()) {
                map.put("message", "Title with this article already exists");
                return "article_editor";
            } else {
                Article newArticle = articleRepository.save(article);
                return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#article").arg(0, newArticle.getSlug()).build();
            }
        }

    }

    @RequestMapping(value = "/edit/{slug}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String edit(@PathVariable String slug, ModelMap map) {
        Optional<Article> article = articleRepository.findBySlug(slug);
        if (article.isPresent()) {
            map.put("article", article.get());
        } else {
            return MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
        }
        return "article_editor";
    }


    @RequestMapping(value = "/edit/{slug}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editPost(@PathVariable String slug, @Valid @ModelAttribute("article") Article newArticle,
                           BindingResult result,
                           ModelMap map) {
        if (result.hasErrors()) {
            return "article_editor";
        } else {
            Optional<Article> oldArticle = articleRepository.findBySlug(slug);
            if (oldArticle.isPresent()) {
                String text = HtmlFilter.filterHtml(newArticle.getText());
                oldArticle.get().setTitle(HtmlFilter.filterHtml(newArticle.getTitle()));
                oldArticle.get().setText(text);
                articleRepository.save(oldArticle.get());
                return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#article").arg(0, oldArticle.get().getSlug()).build();
            } else {
                return MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
            }
        }
    }

    @RequestMapping(value = "/article/{slug}")
    @PreAuthorize("isAuthenticated()")
    public String article(@PathVariable String slug, ModelMap map) {
        Optional<Article> article = articleRepository.findBySlug(slug);
        if (!article.isPresent()) {
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
        } else {
            map.put("article", article.get());
            return "article";
        }
    }


}
