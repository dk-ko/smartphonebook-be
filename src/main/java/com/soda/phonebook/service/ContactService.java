package com.soda.phonebook.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.common.CanNotDeleteContact;
import com.soda.phonebook.common.CanNotFindCategory;
import com.soda.phonebook.common.CanNotSaveContact;
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.ContactDataRequestDto;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.ContactUpdateRequestDto;
import com.soda.phonebook.dto.req.DigitSaveRequestDto;
import com.soda.phonebook.dto.req.DigitUpdateRequestDto;
import com.soda.phonebook.dto.req.InfoSaveRequestDto;
import com.soda.phonebook.dto.req.InfoUpdateRequestDto;
import com.soda.phonebook.dto.req.TagSaveRequestDto;
import com.soda.phonebook.dto.req.TagUpdateRequestDto;
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
	private final CategoryService categoryService;
	
	// read one
	@Transactional(readOnly = true)
	public ContactResponseDto getContacts(Long id) {
		
		Contact findContact = findById(id);
		
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
	public List<ContactListReadResponseDto> getAllContacts(User user) {
//	public List<ContactListReadResponseDto> getAllContacts() {
		
		log.info("* userService.findById");
		User findUser = userService.findByEmail(user.getEmail());
		
		log.info("* contact repository 전");
		List<Contact> findList = contactRepository.findAllByUser(findUser);

		log.info("* repository 후");
		List<ContactListReadResponseDto> dtoList = new ArrayList<>();
		for(Contact contact : findList) {
			dtoList.add(ContactListReadResponseDto.builder()
					.contact(contact)
					.digits(getDigitsResponseDto(contact))
					.build());
		}
		log.info("* dto 변환");
		return dtoList;
	}
	
	// delete
	public void delete(Long id, User user) {
		Contact findContact = findById(id);
		
		if(findContact.getType() == ContactType.ME)
			throw new CanNotDeleteContact("삭제할 수 없습니다.");
		
		User findUser = userService.findByEmail(user.getEmail());
		
		removeContactFromTag(id, findContact);
		deleteFavoritesToUser(findUser, findContact);
		
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
//	public boolean create(ContactSaveRequestDto dto) throws IOException {
	public Long create(ContactSaveRequestDto dto, User user) throws IOException {
		log.info("* contactservice create");
		User findUser = userService.findByEmail(user.getEmail());
		
		Contact contact = dto.toEntity(findUser);
		log.info("* dto.toEntity 후");
		addDigitToContact(contact, dto.getDigits());

		addInfoToContact(contact, dto.getUrls());
		addInfoToContact(contact, dto.getEmails());
		addInfoToContact(contact, dto.getDates());
		addInfoToContact(contact, dto.getAddresses());
		
		addTagToContact(contact, dto.getTags());
		
		log.info("* save 전");
		contactRepository.save(contact);
		log.info("* save 후");
		if(contact.getType()==ContactType.FAVORITED) {
//			User user = userService.getCurrentUser();
			findUser.getFavorites().add(contact);
		}
//		return true;
		return contact.getId();
	}
	
	private void addDigitToContact(Contact contact, List<DigitSaveRequestDto> getDigits) {
		for(DigitSaveRequestDto digitDto : getDigits) 
			contact.getDigits().add(digitDto.toEntity(contact, checkCategory(digitDto)));
	}
	
	private <T extends InfoSaveRequestDto> void addInfoToContact(Contact contact, List<T> getInfoes) {
		for(T infoDto : getInfoes) 
			contact.getInfoes().add(infoDto.toEntity(contact, checkCategory(infoDto)));
	}
	
	private void addTagToContact(Contact contact, Set<TagUpdateRequestDto> getTags) {
		for(TagUpdateRequestDto tagDto : getTags) {
			Tag findTag = tagService.findById(tagDto.getId());
			contact.getTags().add(findTag);
			findTag.getContacts().add(contact);
		}
	}
	
	private void deleteTagToContact(Contact contact) {
		Iterator<Tag> it = contact.getTags().iterator();
		
		while(it.hasNext()) {
			Tag tag = it.next();
			
			// tag에서 contact 제거 
			tag.getContacts().remove(contact);
			// contact에서 tag 제거 
			it.remove();
		}
	}
	
	private <T extends ContactDataRequestDto> Category checkCategory (T data) {
		Optional<Category> findCategory = categoryService.findById(data.getCategory().getId());
		Category category = findCategory.orElseThrow(
				()->new CanNotFindCategory("존재하지 않는 카테고리에 저장"));
		return category;
	}
	
	// edit 
//	public boolean update(Long id, ContactUpdateRequestDto dto) {
	public Long update(Long id, ContactUpdateRequestDto dto, User user) throws IOException {
		
		Contact findContact = findById(id);
		User findUser = userService.findByEmail(user.getEmail());
		
		if(!(id == dto.getId())) 
			throw new CanNotSaveContact("id가 일치하지 않습니다.");
		
		log.info("* contact 의존성 없는 내용 수정");
		findContact.updateContact(dto.toEntity(findUser));
		
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
		
		log.info("* contact tag 수정");
		deleteTagToContact(findContact);
		addTagToContact(findContact, dto.getTags());
		
		log.info("* contact save");
		contactRepository.save(findContact);
		
		log.info("* contact favorite 수정");
//		User user = userService.getCurrentUser();
		switch(dto.getType()) {
		case FAVORITED:
			addFavoritesToUser(findUser, findContact);
			break;
		case DEFAULT:
			deleteFavoritesToUser(findUser, findContact);
			break;
		case ME:
//			throw new CanNotSaveContact("타입을 변경할 수 없습니다.");
			break;
		}
		log.info("* service return 전");	
//		return true;
		return id;
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
	
	private Contact findById(Long id) {
		return contactRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
	}
	
	public boolean addTagToContact(Long id, Long tagId) {
		Contact findContact = findById(id);
		Tag findTag = tagService.findById(tagId);
		
		findContact.getTags().add(findTag);
		findTag.getContacts().add(findContact);
		
		contactRepository.save(findContact);
		return true;
	}
	
	public boolean deleteTagToContact(Long id, Long tagId) {
		Contact findContact = findById(id);
		Tag findTag = tagService.findById(tagId);
		
		findContact.getTags().remove(findTag);
		findTag.getContacts().remove(findContact);
		
		contactRepository.save(findContact);
		return true;
	}
	
	@Transactional(readOnly = true)
	public List<TagResponseDto> getAllTagsByContact(Long id){
		Contact findContact = findById(id);
		List<TagResponseDto> dtoList = new ArrayList<>();
		
		for(Tag tag : findContact.getTags())
			dtoList.add(new TagResponseDto(tag));
		return dtoList;
	}
	
	public boolean addToFavorites(Long id, User user) {
//		User user = userService.getCurrentUser();
		User findUser = userService.findByEmail(user.getEmail());
		Contact findContact = findById(id);
		
		return addFavoritesToUser(findUser, findContact);
	}
	
	public boolean deleteToFavorites(Long id, User user) {
		User findUser = userService.findByEmail(user.getEmail());
		Contact findContact = findById(id);
		
		return deleteFavoritesToUser(findUser, findContact);
	}
	
	private boolean addFavoritesToUser(User user, Contact contact) {
		contact.updateType(ContactType.FAVORITED);
		user.getFavorites().add(contact);
		return true;
	}
	
	private boolean deleteFavoritesToUser(User user, Contact contact) {
		contact.updateType(ContactType.DEFAULT);
		user.getFavorites().remove(contact);
		return true;
	}
	
}
