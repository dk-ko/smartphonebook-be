package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.DigitSaveRequestDto;
import com.soda.phonebook.dto.req.InfoSaveRequestDto;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.DigitResponseDto;
import com.soda.phonebook.dto.res.InfoResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.exception.CanNotFindCategory;
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
	private final DigitService digitService;
	private final InfoService<?> infoService;
	private final CategoryService categoryService;
	
	
	@Transactional(readOnly = true)
	public ContactResponseDto findById(Long id) {
		
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
		
		return ContactResponseDto.builder()
				.contact(findContact)
				.digits(getDigitsResponseDto(findContact))
				.infoes(getInfoesResponseDto(findContact))
				.tags(getTagsResponseDto(findContact)).build();
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
		User currentUser = userService.getCurrentUser();
		
		Contact savedContact = contactRepository.save(dto.toEntity(currentUser));
		
		addDigitToContact(savedContact, dto.getDigits());

		addInfoToContact(savedContact, dto.getUrls(), currentUser);
		addInfoToContact(savedContact, dto.getEmails(), currentUser);
		addInfoToContact(savedContact, dto.getDates(), currentUser);
		addInfoToContact(savedContact, dto.getAddresses(), currentUser);
		
		return Optional.ofNullable(contactRepository.save(savedContact)).isPresent(); 
	}
	
	private void addDigitToContact(Contact contact, List<DigitSaveRequestDto> getDigits) {
		for(DigitSaveRequestDto digitDto : getDigits) {
			Optional<Category> findCategory = categoryService.findById(digitDto.getCategory().getId());
			Category category = findCategory.orElseThrow(
					()->new CanNotFindCategory("존재하지 않는 카테고리에 저장"));
			
			Digit savedDigit = digitService.save(digitDto.toEntity(contact, category));
			contact.getDigits().add(savedDigit);
		}
	}
	
	private <T extends InfoSaveRequestDto> void addInfoToContact(Contact contact, List<T> getInfoes, User user) {
		for(T infoDto : getInfoes) {
			Optional<Category> findCategory = categoryService.findById(infoDto.getCategory().getId());
			Category category = findCategory.orElseThrow(
					()->new CanNotFindCategory("존재하지 않는 카테고리에 저장"));
			
			Info savedInfo = infoService.save(infoDto.toEntity(contact, category));
			contact.getInfoes().add(savedInfo);
		}
	}
	
	// edit 
//	public Contact update(Long id, )
	
}
