package com.tynan.codefellowship.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) throws ParseException {

        Date DOB = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);

        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, DOB, bio);
        appUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String getMainPage() {
        return "mainpage";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        System.out.println(currentUser.firstName);
        m.addAttribute("user", currentUser);
        return "userprofile";
    }



    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable Long id,
            Model m) {
        AppUser queryUser = appUserRepository.findById(id).get();
        System.out.println(queryUser.username);
        m.addAttribute("user", queryUser);
        return "userprofile";
    }
}
