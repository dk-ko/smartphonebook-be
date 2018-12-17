package com.soda.phonebook.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver{
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception ex) {
		
		if(isRestController(handler)) {
			log.info("[RestController is true]");
			log.error(ex.getMessage());
			
			return null;
		}
		
		log.info("[not restController]");
		log.error(ex.getClass() + " : " + ex.getMessage());
		
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("error");

		return mv;
	}
	
	private boolean isRestController(Object handler) {
		if(handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			
			return method.getMethod().getDeclaringClass()
					.isAnnotationPresent(RestController.class);
		}
		return false;
	}

}
