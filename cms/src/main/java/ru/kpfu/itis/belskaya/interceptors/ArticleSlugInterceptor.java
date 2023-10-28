package ru.kpfu.itis.belskaya.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.kpfu.itis.belskaya.models.Article;
import ru.kpfu.itis.belskaya.repositories.ArticleRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;


/**
 * @author Elizaveta Belskaya
 */
@Component
public class ArticleSlugInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String requestUrl = request.getRequestURL().toString();
            String[] parts = requestUrl.split("/");
            String path = parts[parts.length - 1];
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethodAnnotation(RequestMapping.class) != null && !handlerMethod.getMethod().getName().equals("articleSlug")) {
                return true;
            } else if (request.getAttribute("found") != null && handlerMethod.getMethod().getName().equals("articleSlug")) {
                return true;
            }

            if (request.getAttribute("found") == null) {
                Optional<Article> article = articleRepository.findBySlug(path);
                if (article.isPresent()) {
                    request.setAttribute("found", "true");
                    return true;
                } else {
                    Enumeration<String> headerNames = request.getHeaderNames();
                    HttpHeaders headers = new HttpHeaders();
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        String headerValue = request.getHeader(headerName);
                        headers.add(headerName, headerValue);
                    }
                    throw new NoHandlerFoundException("GET", path, headers);
                }
            }
            return true;

        }
        return true;
    }


}
