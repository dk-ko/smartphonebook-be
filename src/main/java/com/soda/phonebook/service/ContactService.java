package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.DigitResponseDto;
import com.soda.phonebook.dto.res.InfoResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
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
	private final TagService tagService;
	
	
	@Transactional(readOnly = true)
	public ContactResponseDto findById(Long id) {
		
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
		
		List<DigitResponseDto> digitsDto = getDigitsResponseDto(findContact);
		List<InfoResponseDto> infoesDto = getInfoesResponseDto(findContact);
		Set<TagResponseDto> tagsDto = getTagsResponseDto(findContact);
		
		return new ContactResponseDto(findContact, digitsDto, infoesDto, tagsDto);
	}
	
	@Transactional(readOnly = true)
	private List<DigitResponseDto> getDigitsResponseDto(Contact contact) {
		List<DigitResponseDto> digitsDto = new ArrayList<>();
		for(Digit d : contact.getDigits())
			digitsDto.add(new DigitResponseDto(d));
		return digitsDto;
	}
	
	@Transactional(readOnly = true)
	private List<InfoResponseDto> getInfoesResponseDto(Contact contact){
		List<InfoResponseDto> infoesDto = new ArrayList<>();
		for(Info i : contact.getInfoes())
			infoesDto.add(new InfoResponseDto(i));
		return infoesDto;
	}
	
	@Transactional(readOnly = true)
	private Set<TagResponseDto> getTagsResponseDto(Contact contact){
		Set<TagResponseDto> tagsDto = new LinkedHashSet<>();
		for(Tag t : contact.getTags())
			tagsDto.add(new TagResponseDto(t));
		return tagsDto;
	}
	
	@Transactional(readOnly = true)
	public List<ContactListReadResponseDto> findAll() {
		List<Contact> findList = contactRepository.findAll();
		List<ContactListReadResponseDto> dtoList = new ArrayList<>();
		for(Contact contact : findList)
			dtoList.add(new ContactListReadResponseDto(contact));
		return dtoList;
	}
	
	public void delete(Long id) {
		Optional<Contact> findContact = contactRepository.findById(id);
		if(!findContact.isPresent())
			throw new IllegalArgumentException("delete error : wrong id");
		
		removeContactFromTag(id, findContact);
		
		contactRepository.deleteById(id);
	}
	
	private void removeContactFromTag(Long id, Optional<Contact> contact) {
		Set<Tag> findTags = tagService.findAllByContact(id);
		if(!findTags.isEmpty())
			for(Tag tag : findTags) 
				tag.getContacts().remove(contact.get());
		else
			log.info("tag 없음");
	}
	
	public boolean create(ContactSaveRequestDto dto) {
		// to-do : name, type null check
		Contact contact = dto.toEntity();
		dto.getDigits();
		dto.getInfoes();
		dto.getTags();
		
		contact.updateUser(userService.getCurrentUser());
		
		return Optional.ofNullable(contactRepository.save(contact)).isPresent(); 
	}
	
	// edit 
//	public Contact update(Long id, )
	
}
