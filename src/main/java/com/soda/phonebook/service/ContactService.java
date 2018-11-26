package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.DigitResponseDto;
import com.soda.phonebook.dto.res.InfoResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.TagRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ContactService {

	private final ContactRepository contactRepository;
	private final TagRepository tagRepository;
	
	private final UserService userService;
	
	@Transactional(readOnly = true)
	public ContactResponseDto findById(Long id) {
		log.info("findContact 전");
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
		
		List<DigitResponseDto> digitsDto = new ArrayList<>();
		for(Digit d : findContact.getDigits())
			digitsDto.add(new DigitResponseDto(d));
		
		List<InfoResponseDto> infoesDto = new ArrayList<>();
		for(Info i : findContact.getInfoes())
			infoesDto.add(new InfoResponseDto(i));
		
		Set<TagResponseDto> tagsDto = new LinkedHashSet<>();
		for(Tag t : findContact.getTags())
			tagsDto.add(new TagResponseDto(t));
		
		return new ContactResponseDto(findContact, digitsDto, infoesDto, tagsDto);
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
		if(!contactRepository.findById(id).isPresent())
			throw new IllegalArgumentException("delete error : wrong id");
		
		// contact id와 일치하는 tag를 찾아서 contac_id만 삭제하도록 수정 
		
		contactRepository.deleteById(id);
	}
	
	public boolean create(ContactSaveRequestDto dto) {
		// to-do : name, type null check
		Contact saveContact = dto.toEntity();
		saveContact.updateUser(userService.getCurrentUser());
		
		return Optional.ofNullable(contactRepository.save(saveContact)).isPresent(); 
	}
	
	// edit 
//	public Contact update(Long id, )
	
}
