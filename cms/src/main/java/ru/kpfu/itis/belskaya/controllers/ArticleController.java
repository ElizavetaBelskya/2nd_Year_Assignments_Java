package ru.kpfu.itis.belskaya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import ru.kpfu.itis.belskaya.models.Article;
import ru.kpfu.itis.belskaya.repositories.ArticleRepository;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Elizaveta Belskaya
 */

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    private static final int PAGE_SIZE = 3;

    @RequestMapping(value = "/{slug}")
    public String articleSlug(@PathVariable String slug, ModelMap map) {
        Optional<Article> article = articleRepository.findBySlug(slug);
        if (!article.isPresent()) {
//            обработка ситуации, когда нет подходящих статей, происходит в интерцепторе
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("AC#list").arg(0, 1).build();
        } else {
            map.put("article", article.get());
            return "article";
        }
    }

    @GetMapping("/cached-page")
    public void getCachedPage(HttpServletResponse response) {
        response.setHeader("Cache-Control", "max-age=60");
        response.setHeader("Pragma", "cache");
        response.setDateHeader("Expires", System.currentTimeMillis() + 60 * 1000);
        String pageContent = "<html><body><h1>Закэшированная страница</h1></body></html>";
        try {
            response.getWriter().write(pageContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
