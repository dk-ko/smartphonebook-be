package com.soda.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@Controller
@AllArgsConstructor
public class MainController {
	@GetMapping(value = {"/","/index"})
	public String index() {
//	   return "forward:index.html";
//		return "static/index.html";
		return "index";
	}
	
//	@GetMapping(value = {"/","/index"})
//	public ModelAndView start() {
//		return new ModelAndView("index");
//	}
}
