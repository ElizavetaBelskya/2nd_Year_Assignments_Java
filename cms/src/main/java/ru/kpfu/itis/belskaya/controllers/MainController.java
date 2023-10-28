package ru.kpfu.itis.belskaya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.belskaya.models.User;
import ru.kpfu.itis.belskaya.models.forms.LoginForm;
import ru.kpfu.itis.belskaya.services.UserService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Collection;

/**
 * @author Elizaveta Belskaya
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String empty() {
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("MC#login").build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @PermitAll
    public String register(ModelMap map) {
        map.put("user", new User());
        return "validation_signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @PermitAll
    public String registerPost(RedirectAttributes redirectAttributes,
                                   @Valid @ModelAttribute("user") User user,
                                   BindingResult result,
                                   ModelMap map) {
        if (result.hasErrors()) {
            return "validation_signup";
        } else {
            try {
                userService.registerUser(user);
                map.put("loginForm", new LoginForm(user.getUsername(), user.getPassword()));
                return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("MC#login").build();
            } catch (DuplicateKeyException e) {
                map.put("message", e.getMessage());
                return "validation_signup";
            }
        }

    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(required = false) String error,
                        @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult result,
                        ModelMap map) {
        map.put("error", error);
        return "login_form";
    }

    @RequestMapping(value = "/403")
    @PreAuthorize("isAuthenticated()")
    public String forbidden() {
        return "forbidden";
    }


    @RequestMapping(value = "/logout")
    @PreAuthorize("isAuthenticated()")
    public String logout() {
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("MC#login").build();
    }




}
