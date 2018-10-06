package com.sode.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

//import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sode.phonebook.repository.ContactRepository;
import com.sode.phonebook.domain.Contact;

@ActiveProfiles("set1")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactRepositoryTest {

	@Autowired
	ContactRepository contactRepository;
//	
//	@After
//	public void cleanup() {
//		/*
//		 * 이후 테스트 코드에 영향을 미치지 않기 위해
//		 * 테스트 메소드가 끝날 때마다 repository 전체를 비우는 코
//		 */
//		contactRepository.deleteAll();
//	}
	
	@Test
	public void 연락처저장_불러오기() {
		// given
//		Contact contact = new Contact();
//		
//		contact.setUser(new User());
//		contact.setName("고다경");
//		contact.setMemo("memo");
//		contact.setBirth("1993-07-30");
//		contact.setPhoto(null);
//		contact.setFavorite(0);
//		contact.setSite(null);
//		
//		contactRepository.save(contact);
				
		// when
		List<Contact> contactList = contactRepository.findAll();
		
		// then
		Contact c = contactList.get(0);
		assertThat(c.getName(), is("고다경"));
		assertThat(c.getMemo(), is("memo"));
	}
}
