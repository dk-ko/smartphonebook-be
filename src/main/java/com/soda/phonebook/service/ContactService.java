package com.soda.phonebook.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.repository.ContactRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ContactService {

	private final ContactRepository contactRepository;
	private final UserService userService;
	
	@Transactional(readOnly = true)
	public Contact findById(Long id) {
		return contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
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
		// if name is null
		Contact saveContact = dto.toEntity();
		saveContact.updateUser(userService.getCurrentUser());
		
		return contactRepository.save(saveContact);
	}
	
	// edit 
//	public Contact update(Long id, )
	
}
