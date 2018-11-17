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
	
	public Contact findById(Long id) {
		return contactRepository.findOne(id);
	}
	
	// 전체 Contact 목록 조회 
	public List<Contact> findAll() {
		return contactRepository.findAll(); 
	}
	
	public void delete(Long id) {
		contactRepository.delete(id);
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
