package com.soda.phonebook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.soda.phonebook.common.AuthenticationException;
import com.soda.phonebook.common.HttpSessionUtils;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.security.SessionConstants;
import com.soda.phonebook.service.CategoryService;
import com.soda.phonebook.service.TagService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/api")
public class MainController {

    private HttpSession httpSession;
    private TagService tagService;
    private CategoryService categoryService;

    
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
    public String isAuth(HttpSession session) {
    		User user = (User)httpSession.getAttribute(SessionConstants.LOGIN_USER);
    		
    		if(user == null) throw new AuthenticationException("로그인에 실패했습니다.");
    		log.info("* default tag create");
    		tagService.createDafaultTag(HttpSessionUtils.getUserFromSession(session));
    		log.info("* default category create");
    		categoryService.createDefaultCategory(HttpSessionUtils.getUserFromSession(session));
    		
    		return "redirect:/#/list";
    }
}
