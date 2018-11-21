package com.soda.phonebook.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
@ActiveProfiles("local")
@SpringBootTest
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
				.rep(Mark.Y)
				.build();
	}
	
	@Test
	@Transactional
	public void test_mark_converter() {
		em.persist(digit);
		em.flush();
		em.clear();
//		Digit savedDigit = digitRepository.save(digit);
//		em.persist(digit);
		
		Query query = em.createNativeQuery("select * from digit where representation = :representation", Digit.class);
		query.setParameter("representation", 1);
		List<Digit> list = query.getResultList();
		
		Mark resultMark = list.get(0).getRep();
		assertThat(Mark.Y, is(resultMark));
		
		// native query 
//		Query query = em.createNativeQuery(
//				"select * from digit where representation = \"Y\"", Digit.class);
//		Digit findDigit = (Digit) query.getResultList().get(0);
//		assertThat(findDigit.getRepresentation(), is(1)); // Mark.N is 0.
		
		// confirm
//		assertThat(findDigit.getRepresentation(), is(Mark.Y));
	}

//	@After
//	void end() {
//		digitRepository.deleteAll();
//		contactRepository.deleteAll();
//		categoryRepository.deleteAll();
//	}
}
