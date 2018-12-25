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
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.repository.CategoryRepository;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.TagRepository;
import com.soda.phonebook.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GoogleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private HttpSession httpSession;
	private ObjectMapper objectMapper;
	
	private UserRepository userRepository;
	private ContactRepository contactRepository;
	private TagRepository tagRepository;
	private CategoryRepository categoryRepository;
	
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
        
        httpSession.setMaxInactiveInterval(60*60);
        response.sendRedirect("/#/list");
//        response.sendRedirect("/api/isAuth");
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
            User newUser = google.toEntity();
            newUser.updateRole(SessionConstants.LOGIN_USER);
            savedUser = userRepository.save(newUser);
            
            log.info("* default tag create");
//            createDafaultTag(savedUser);
            Tag tag1 = Tag.builder().name("가족").user(savedUser).build();
    			Tag tag2 = Tag.builder().name("친구").user(savedUser).build();
            Tag tag3 = Tag.builder().name("학교").user(savedUser).build();
            Tag tag4 = Tag.builder().name("직장").user(savedUser).build();
            
            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            tagRepository.save(tag4);
            
            log.info(" default category create");
//            createDefaultCategory(savedUser);
    			Category digit1 = Category.builder().type(DataType.DIGIT).name("휴대전화").user(savedUser).build();
    			Category digit2 = Category.builder().type(DataType.DIGIT).name("집").user(savedUser).build();
    			Category digit3 = Category.builder().type(DataType.DIGIT).name("직장").user(savedUser).build();
    			Category digit4 = Category.builder().type(DataType.DIGIT).name("팩스").user(savedUser).build();
    			Category digit5 = Category.builder().type(DataType.DIGIT).name("기타").user(savedUser).build();
    		
    			categoryRepository.save(digit1);
    			categoryRepository.save(digit2);
    			categoryRepository.save(digit3);
    			categoryRepository.save(digit4);
    			categoryRepository.save(digit5);
    		
    			Category url1 = Category.builder().type(DataType.URL).name("개인").user(savedUser).build();
    			Category url2 = Category.builder().type(DataType.URL).name("직장").user(savedUser).build();
    			Category url3 = Category.builder().type(DataType.URL).name("기타").user(savedUser).build();
    		
    			categoryRepository.save(url1);
    			categoryRepository.save(url2);
    			categoryRepository.save(url3);
    		
    			Category email1 = Category.builder().type(DataType.EMAIL).name("개인").user(savedUser).build();
    			Category email2 = Category.builder().type(DataType.EMAIL).name("직장").user(savedUser).build();
    			Category email3 = Category.builder().type(DataType.EMAIL).name("기타").user(savedUser).build();
    		
    			categoryRepository.save(email1);
    			categoryRepository.save(email2);
    			categoryRepository.save(email3);
    		
    			Category date1 = Category.builder().type(DataType.DATE).name("생일").user(savedUser).build();
    			Category date2 = Category.builder().type(DataType.DATE).name("기념일").user(savedUser).build();
    			Category date3 = Category.builder().type(DataType.DATE).name("기타").user(savedUser).build();
    		
    			categoryRepository.save(date1);
    			categoryRepository.save(date2);
    			categoryRepository.save(date3);
    		
    			Category address1 = Category.builder().type(DataType.ADDRESS).name("집").user(savedUser).build();
    			Category address2 = Category.builder().type(DataType.ADDRESS).name("직장").user(savedUser).build();
    			Category address3 = Category.builder().type(DataType.ADDRESS).name("기타").user(savedUser).build();
    		
    			categoryRepository.save(address1);
    			categoryRepository.save(address2);
    			categoryRepository.save(address3);
            
            
            Contact defaultContact = Contact.builder()
            		.user(savedUser)
            		.type(ContactType.ME)
            		.name(google.getName())
            		.photo("".getBytes())
//            		.photo(Base64.getDecoder().decode(google.getPicture()))
            		.build();
            log.debug("* contact build");
            		
            contactRepository.save(defaultContact);
            log.debug("* contact save");
        }

        log.debug("* savedUser return 전 ");
        return savedUser;
    }
	/*
	private void createDafaultTag(User user) {
		Tag tag1 = Tag.builder().name("가족").user(user).build();
		Tag tag2 = Tag.builder().name("친구").user(user).build();
        Tag tag3 = Tag.builder().name("학교").user(user).build();
        Tag tag4 = Tag.builder().name("직장").user(user).build();
        
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);
	}*/
	
	private void createDefaultCategory(User user) {
		Category digit1 = Category.builder().type(DataType.DIGIT).name("휴대전화").user(user).build();
		Category digit2 = Category.builder().type(DataType.DIGIT).name("집").user(user).build();
		Category digit3 = Category.builder().type(DataType.DIGIT).name("직장").user(user).build();
		Category digit4 = Category.builder().type(DataType.DIGIT).name("팩스").user(user).build();
		Category digit5 = Category.builder().type(DataType.DIGIT).name("기타").user(user).build();
		
		categoryRepository.save(digit1);
		categoryRepository.save(digit2);
		categoryRepository.save(digit3);
		categoryRepository.save(digit4);
		categoryRepository.save(digit5);
		
		Category url1 = Category.builder().type(DataType.URL).name("개인").user(user).build();
		Category url2 = Category.builder().type(DataType.URL).name("직장").user(user).build();
		Category url3 = Category.builder().type(DataType.URL).name("기타").user(user).build();
		
		categoryRepository.save(url1);
		categoryRepository.save(url2);
		categoryRepository.save(url3);
		
		Category email1 = Category.builder().type(DataType.EMAIL).name("개인").user(user).build();
		Category email2 = Category.builder().type(DataType.EMAIL).name("직장").user(user).build();
		Category email3 = Category.builder().type(DataType.EMAIL).name("기타").user(user).build();
		
		categoryRepository.save(email1);
		categoryRepository.save(email2);
		categoryRepository.save(email3);
		
		Category date1 = Category.builder().type(DataType.DATE).name("생일").user(user).build();
		Category date2 = Category.builder().type(DataType.DATE).name("기념일").user(user).build();
		Category date3 = Category.builder().type(DataType.DATE).name("기타").user(user).build();
		
		categoryRepository.save(date1);
		categoryRepository.save(date2);
		categoryRepository.save(date3);
		
		Category address1 = Category.builder().type(DataType.ADDRESS).name("집").user(user).build();
		Category address2 = Category.builder().type(DataType.ADDRESS).name("직장").user(user).build();
		Category address3 = Category.builder().type(DataType.ADDRESS).name("기타").user(user).build();
		
		categoryRepository.save(address1);
		categoryRepository.save(address2);
		categoryRepository.save(address3);
	}
}
