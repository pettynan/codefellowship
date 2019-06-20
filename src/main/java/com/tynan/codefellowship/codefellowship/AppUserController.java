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
import java.util.List;
import java.util.Set;

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
    public String getSignupPage(Principal p, Model m) {
        m.addAttribute("principal", p);
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m) {
        m.addAttribute("principal", p);
        return "login";
    }

    @GetMapping("/")
    public String getMainPage(Principal p, Model m) {
        m.addAttribute("principal", p);
        return "mainpage";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("user", currentUser);
        return "userprofile";
    }

    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable Long id, Model m, Principal p) {
        AppUser queryUser = appUserRepository.findById(id).get();
        m.addAttribute("user", queryUser);
        m.addAttribute("principal", p);
        return "userprofile";
    }

    @PostMapping("/users/{id}/follow")
    public RedirectView followUser(@PathVariable Long id, Principal p, Model m) {
        // Get the currently logged in user and target user objects
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        AppUser followedUser = appUserRepository.findById(id).get();

        // Make them follow/be followed by each other
        currentUser.following.add(followedUser);
        followedUser.followers.add(currentUser);

        // Save changes to the repository
        appUserRepository.save(currentUser);
        appUserRepository.save(followedUser);

        return new RedirectView("/users/" + id);
    }

    @GetMapping("/userindex")
    public String getUserIndex(Model m) {
        Iterable<AppUser> allUsers = appUserRepository.findAll();
        m.addAttribute("allUsers", allUsers);
        return "userIndex";
    }

}
