package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.info.Address;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.repository.AddressRepository;
import com.soda.phonebook.repository.CategoryRepository;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InfoTest {
	
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	private Category category;
	private Address address;
	private Contact contact;
	private User user;
	
	@Before
	public void setUp() throws Exception{
		user = userRepository.save((User.builder().name("유저1").build()));
		
		contact = contactRepository.save(
				Contact.builder()
				.name("고다경")
				.memo("skhu")
//				.user(user)
				.type(ContactType.DEFAULT)
				.build());
		
		category = categoryRepository.save(Category.builder()
				.name("학교")
				.type(DataType.ADDRESS)
				.user(user)
				.build());
		
		// fk 지정하지 않아도 테스트는 통과함 
		address = Address.builder()
				.contents("서울시 구로구 항동")
				.category(category)
				.contact(contact)
				.build();
	}
	
	@Test 
	public void test_상속관계() throws Exception{
		addressRepository.save(address);
		
		Address savedAddress = addressRepository.findAll().get(0);
		
		assertThat(savedAddress.getContents(), is("서울시 구로구 항동"));
//		assertThat(savedAddress.getCategory().getType(), is(Type.ADDRESS));
	}

	@Test
	public void testAddressCreate() {
		User user = MockEntity.mock(User.class, 1l);
		user.updateName("고다경");
		
		Category c1 = Category.builder()
				.name("집")
				.type(DataType.ADDRESS)
				.user(user).build();
		
		Category c2 = Category.builder()
				.name("회사")
				.type(DataType.ADDRESS)
				.user(user).build();
		
		Info address = Address.builder()
				.contents("서울 구로구 항동")
				.category(c2)
				.build();
		
		assertThat(address.getContents(), is("서울 구로구 항동"));
		assertThat(address.getCategory().getName(), is("회사"));
		assertThat(address.getCategory().getType(), is(DataType.ADDRESS));
		
		address.updateCategory(c1);
		assertThat(address.getCategory(), is(c1));
	}

}
