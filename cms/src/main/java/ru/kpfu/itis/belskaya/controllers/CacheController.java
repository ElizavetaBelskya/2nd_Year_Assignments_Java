package ru.kpfu.itis.belskaya.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class CacheController {

    @GetMapping(value = "/cache/{word}")
    public void getData(@PathVariable String word, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (Arrays.stream(request.getCookies()).noneMatch(x -> Objects.equals(x.getName(), "word"))) {
            Cookie cookie = new Cookie("word", word);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            try {
                response.getWriter().write(word);
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Cookie cookie = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("word")).findFirst().get();
            try {
                response.getWriter().write(cookie.getValue());
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
