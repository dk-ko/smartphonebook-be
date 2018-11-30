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
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactDataRequestDto;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.ContactUpdateRequestDto;
import com.soda.phonebook.dto.req.DigitSaveRequestDto;
import com.soda.phonebook.dto.req.DigitUpdateRequestDto;
import com.soda.phonebook.dto.req.InfoSaveRequestDto;
import com.soda.phonebook.dto.req.InfoUpdateRequestDto;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.DigitResponseDto;
import com.soda.phonebook.dto.res.InfoResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.exception.CanNotFindCategory;
import com.soda.phonebook.exception.CanNotUpdateContact;
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
	private final CategoryService categoryService;
	
	
	// read one
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
	
	// read all
	@Transactional(readOnly = true)
	public List<ContactListReadResponseDto> findAll() {
		List<Contact> findList = contactRepository.findAll();
		List<ContactListReadResponseDto> dtoList = new ArrayList<>();
		for(Contact contact : findList)
			dtoList.add(new ContactListReadResponseDto(contact));
		return dtoList;
	}
	
	// delete
	public void delete(Long id) {
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("delete error : wrong id"));
		
		removeContactFromTag(id, findContact);
		
		contactRepository.deleteById(id);
	}
	
	private void removeContactFromTag(Long id, Contact contact) {
		Set<Tag> findTags = tagService.findAllByContact(id);
		
		if(contact.getTags().size()!=0) {
			contact.getTags().clear();
			for(Tag tag : findTags) 
				tag.getContacts().remove(contact);
		}else {
			log.info("tag 없음");
		}
	}
	
	// create
	public boolean create(ContactSaveRequestDto dto) {
		
		Contact contact = dto.toEntity(userService.getCurrentUser());

		addDigitToContact(contact, dto.getDigits());

		addInfoToContact(contact, dto.getUrls());
		addInfoToContact(contact, dto.getEmails());
		addInfoToContact(contact, dto.getDates());
		addInfoToContact(contact, dto.getAddresses());
		
		return Optional.ofNullable(contactRepository.save(contact)).isPresent(); 
	}
	
	private void addDigitToContact(Contact contact, List<DigitSaveRequestDto> getDigits) {
		for(DigitSaveRequestDto digitDto : getDigits) {
			contact.getDigits().add(digitDto.toEntity(contact, checkCategory(digitDto)));
		}
	}
	
	private <T extends InfoSaveRequestDto> void addInfoToContact(Contact contact, List<T> getInfoes) {
		for(T infoDto : getInfoes) {
			contact.getInfoes().add(infoDto.toEntity(contact, checkCategory(infoDto)));
		}
	}
	
	private <T extends ContactDataRequestDto> Category checkCategory (T data) {
		Optional<Category> findCategory = categoryService.findById(data.getCategory().getId());
		Category category = findCategory.orElseThrow(
				()->new CanNotFindCategory("존재하지 않는 카테고리에 저장"));
		return category;
	}
	
	// edit 
	public boolean update(Long id, ContactUpdateRequestDto dto) {
		
		Contact findContact = contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
		
		if(!(id == dto.getId())) 
			throw new CanNotUpdateContact("id가 일치하지 않습니다.");
		
		log.info("* contact 의존성 없는 내용 수정");
		findContact.updateContact(dto.toEntity(userService.getCurrentUser()));
		
		log.info("* contact digit 수정");
		List<Digit> digitList = dtoToDigitList(findContact, dto.getDigits());
		findContact.getDigits().clear();
		findContact.getDigits().addAll(digitList);

		log.info("* contact info 수정");
		List<Info> infoList = new ArrayList<>();
		infoList.addAll(dtoToInfoList(findContact, dto.getUrls()));
		infoList.addAll(dtoToInfoList(findContact, dto.getEmails()));
		infoList.addAll(dtoToInfoList(findContact, dto.getDates()));
		infoList.addAll(dtoToInfoList(findContact, dto.getAddresses()));
		findContact.getInfoes().clear();
		findContact.getInfoes().addAll(infoList);
		
		contactRepository.save(findContact);
		
		return true;
	}
	
	private List<Digit> dtoToDigitList(Contact contact, List<DigitUpdateRequestDto> dtoList){
		List<Digit> digitList = new ArrayList<>();
		for(DigitUpdateRequestDto dto : dtoList) 
			digitList.add(dto.toEntity(contact, checkCategory(dto)));
		return digitList;
	}
	
	private <T extends InfoUpdateRequestDto> List<Info> dtoToInfoList(Contact contact, List<T> dtoList) {
		List<Info> infoList = new ArrayList<>();
		for(T dto : dtoList)
			infoList.add(dto.toEntity(contact, checkCategory(dto)));
		return infoList;
	}
}
