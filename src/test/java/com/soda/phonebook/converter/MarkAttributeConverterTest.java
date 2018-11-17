package com.soda.phonebook.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.Numbers;
import com.soda.phonebook.repository.CategoryRepository;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.DigitRepository;
import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MarkAttributeConverterTest {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private DigitRepository digitRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private Digit digit;
	
	@Before
	public void setUp() {
		User user = User.builder()
				.name("테스트유저")
				.build();
		
		User savedUser = userRepository.save(user);
		
		Contact contact = Contact.builder()
				.user(savedUser)
				.name("연락처 A")
				.type(ContactType.DEFAULT)
				.build();
		
		Contact savedContact = contactRepository.save(contact);
		
		Category category = Category.builder()
				.name("집")
				.type(DataType.DIGIT)
				.user(savedUser)
				.build();
		
		Category savedCategory = categoryRepository.save(category);
		
		Numbers numbers = Numbers.builder()
				.first("010")
				.second("1234")
				.third("5678").build();
		
		digit = Digit.builder()
				.contact(savedContact)
				.category(savedCategory)
				.numbers(numbers)
				.representation(Mark.N)
				.build();
	}
	
	@Test
	public void test_mark_converter() {
		Digit savedDigit = digitRepository.save(digit);
		
		// native query 
		Query query = em.createNativeQuery(
				"select * from digit where representation = 0", Digit.class);
		assertThat(query.getFirstResult(), is(0)); // Mark.N is 0.
		
		// confirm
		assertThat(savedDigit.getRepresentation(), is(Mark.N));
	}

}
