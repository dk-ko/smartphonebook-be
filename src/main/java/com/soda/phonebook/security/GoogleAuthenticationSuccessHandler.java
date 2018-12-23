package com.soda.phonebook.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private HttpSession httpSession;
	private ObjectMapper objectMapper;
	private UserRepository userRepository;
	private ContactRepository contactRepository;
	
	public GoogleAuthenticationSuccessHandler(HttpSession httpSession, ObjectMapper objectMapper, UserRepository userRepository, ContactRepository contactRepository) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        httpSession.setAttribute(SessionConstants.LOGIN_USER, getUser(getGoogleUser(authentication)));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        httpSession.setMaxInactiveInterval(60*30);
        response.sendRedirect("/");
    }
	
	private GoogleUser getGoogleUser(Authentication authentication) { 
		log.debug("* getgoogleuser");
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        log.debug("* getgoogleuser return 전");
        return objectMapper.convertValue(oAuth2Authentication.getUserAuthentication().getDetails(), GoogleUser.class);
    }
	
	private User getUser(GoogleUser google){
		log.debug("* findByEmail 전");
        User savedUser = userRepository.findByEmail(google.getEmail());

        if(savedUser == null){
        		log.debug("* savedUser == null");
            User newUser = google.toEntity();
            log.debug("* google.toEntity");
            newUser.updateRole(SessionConstants.LOGIN_USER);
            log.debug("* updateRole");
            savedUser = userRepository.save(newUser);
            log.debug("* user save");
            Contact defaultContact = Contact.builder()
            		.user(savedUser)
            		.type(ContactType.ME)
            		.name(google.getName())
//            		.photo(Base64.getDecoder().decode(google.getPicture()))
            		.build();
            log.debug("* contact build");
            		
            contactRepository.save(defaultContact);
            log.debug("* contact save");
        }

        log.debug("* savedUser return 전 ");
        return savedUser;
    }
}
