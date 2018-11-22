package com.soda.phonebook.service;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<Contact> findById(Long id) {
		return contactRepository.findById(id);
	}
	
	// 전체 Contact 목록 조회 
	public List<Contact> findAll() {
		return contactRepository.findAll(); 
	}
	
	public void delete(Long id) {
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
