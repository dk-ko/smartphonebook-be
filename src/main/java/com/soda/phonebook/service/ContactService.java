package com.soda.phonebook.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.repository.ContactRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ContactService {

	private final ContactRepository contactRepository;
	private final UserService userService;
	private final DigitService digitService;
	private final InfoService infoService;
	private final TagService tagService;
	
	@Transactional(readOnly = true)
	public ContactResponseDto findById(Long id) {
		log.info("findContact 전");
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
		
		// 성능 향상을 위해 1:N에서 조회하는 것이 아닌 N:1에서 조회 
		List<Digit> digits = digitService.findAllByContact(id);
		List<Info> infoes = infoService.findAllByContact(id);
		Set<Tag> tags = tagService.findAllByContact(id);
		
		return new ContactResponseDto(findContact, digits, infoes, tags);
	}

	
	@Transactional(readOnly = true)
	public List<Contact> findAll() {
		List<Contact> result = contactRepository.findAll();
		return result;
	}
	
	public void delete(Long id) {
		if(!contactRepository.findById(id).isPresent())
			throw new IllegalArgumentException("delete error : wrong id");
		contactRepository.deleteById(id);
	}
	
	public Contact create(ContactSaveRequestDto dto) {
		// to-do : name, type null check
		Contact saveContact = dto.toEntity();
		saveContact.updateUser(userService.getCurrentUser());
		
		return contactRepository.save(saveContact);
	}
	
	// edit 
//	public Contact update(Long id, )
	
}
