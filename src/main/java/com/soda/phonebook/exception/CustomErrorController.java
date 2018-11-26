package com.soda.phonebook.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class CustomErrorController implements ErrorController{
	
	
	@RequestMapping("/error")
	public <T extends Exception> String returnMain(HttpServletRequest request) {
		
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		
		log.info("[CustomErrorController]");
		log.error("[statusCode]: "+statusCode+", [exception]: "+exception);
		
//		return "redirect:/api/contacts";
		return "/hello";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}
