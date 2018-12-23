package com.soda.phonebook.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soda.phonebook.domain.User;
import com.soda.phonebook.security.SessionConstants;

@RestController
public class MainController {

    private HttpSession httpSession;

    public MainController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    
    @GetMapping("/me")
    public String me(){
    		User user = (User)httpSession.getAttribute(SessionConstants.LOGIN_USER);
        StringBuilder builder = new StringBuilder();
        return builder.append("username : ")
        			.append(user.getName())
        			.append("\nuseremail :")
        			.append(user.getEmail())
        			.toString();
    }

}
