package com.tynan.codefellowship.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PostController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PostRepository postRepository;


    @GetMapping("/post")
    public String getNewPostPage() {return "createPost";}

    @PostMapping("/post")
    public RedirectView addPost(String body, Principal p) {
        Post newPost = new Post();
        newPost.body = body;
        newPost.author = appUserRepository.findByUsername(p.getName());
        newPost.createdAt = new Timestamp(System.currentTimeMillis());
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }


}
