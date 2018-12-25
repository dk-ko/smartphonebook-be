package com.soda.phonebook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.soda.phonebook.common.AuthenticationException;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.security.SessionConstants;

@Controller
@RequestMapping("/api")
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

    @GetMapping("/isAuth")
    public String isAuth() {
    		User user = (User)httpSession.getAttribute(SessionConstants.LOGIN_USER);
//    		if(user == null) return "redirect:/#/";
    		if(user == null) throw new AuthenticationException("로그인에 실패했습니다.");
//    		ModelAndView model;
//    		model.setViewName(viewName);
    		return "redirect:/#/list";
    }
}
