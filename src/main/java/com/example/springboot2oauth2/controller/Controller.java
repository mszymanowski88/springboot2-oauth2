package com.example.springboot2oauth2.controller;

import com.example.springboot2oauth2.security.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    private int userLogginAttempts = 0;
    private int adminLoggingAttempts = 0;
    private int unknownLoggingAttempts = 0;

    private SpringSecurityConfig springSecurityConfig;

    @Autowired
    public Controller(SpringSecurityConfig springSecurityConfig) {
        this.springSecurityConfig = springSecurityConfig;
    }

    @GetMapping("/for-all")
    public String getAll() {
        return "hello all!";
    }

    @GetMapping("/for-user")
    public String getUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return "Hello user " + ((UserDetails) principal).getUsername() + " you logged "
                + (++userLogginAttempts) + " times";


    }

    @GetMapping("/for-admin")
    public String getAdmin() {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "Hello user " + ((UserDetails) principal).getUsername() + " you logged "
                + ++adminLoggingAttempts + " times";
    }

    @GetMapping("/for-uknown")
    public String getUnknown() {


        return "hello unknown " + " you logged "
                + numberOfLogginAttempts(++unknownLoggingAttempts) + " times";
    }

    @GetMapping("/bye")
    public String getBye() {
        return "bye!";
    }

    public int numberOfLogginAttempts(int totalAttempts) {

        int loggingAttempts = totalAttempts;

        String userNameStr = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userNameStr == SecurityContextHolder.getContext().getAuthentication().getName()) {
            loggingAttempts++;

        }
        return loggingAttempts;

    }

}





