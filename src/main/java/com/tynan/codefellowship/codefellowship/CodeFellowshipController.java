package com.tynan.codefellowship.codefellowship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeFellowshipController {

    @GetMapping("/codefellowship")
    public String getCodefellowship() {
        return "they're taking the hobbits to isengaard";
    }


}
