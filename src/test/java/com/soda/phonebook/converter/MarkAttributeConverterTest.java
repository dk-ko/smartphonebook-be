package com.soda.phonebook.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
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


@RunWith(SpringRunner.class)
//@ActiveProfiles("local")
//@SpringBootTest
@DataJpaTest
public class MarkAttributeConverterTest {

	@PersistenceContext
    private EntityManager em;
	
	private Digit digit;
	private User user;
	private Contact contact;
	private Category category;
	private Numbers numbers;
	
	@Before
	public void setUp() {
		user = User.builder()
				.name("테스트유저")
				.build();
		em.persist(user);
		
		contact = Contact.builder()
				.user(user)
				.name("연락처 A")
				.type(ContactType.DEFAULT)
				.build();
		em.persist(contact);
		
		category = Category.builder()
				.name("집")
				.type(DataType.DIGIT)
				.user(user)
				.build();
		em.persist(category);
		
		numbers = Numbers.builder()
				.first("010")
				.second("1234")
				.third("5678").build();
		
		digit = Digit.builder()
				.contact(contact)
				.category(category)
				.numbers(numbers)
				.rep(Mark.Y)
				.build();
		
		em.persist(digit);
		em.flush();
		em.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void test_mark_converter() {

		// native query 
		Query query = em.createNativeQuery("select * from digit where rep = :rep", Digit.class);
		query.setParameter("rep", 1); // Y is 1
		List<Digit> list = query.getResultList();
		
		// confirm
		Mark resultMark = list.get(0).getRep();
		assertThat(Mark.Y, is(resultMark));
		
	}
}
