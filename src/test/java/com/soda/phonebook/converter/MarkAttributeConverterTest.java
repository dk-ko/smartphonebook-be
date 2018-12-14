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
import org.springframework.context.annotation.Import;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.config.JpaAuditConfiguration;
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;

@Import(value = JpaAuditConfiguration.class)
@RunWith(SpringRunner.class)
//@ActiveProfiles("local")
//@SpringBootTest
@DataJpaTest
public class MarkAttributeConverterTest {

	@PersistenceContext
    private EntityManager em;
	
	private User user;
	private Contact contact;
	private Category category;
	
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
		
		em.flush();
		em.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void test_mark_converter() {

		// native query 
		Query query = em.createNativeQuery("select * from category where is_default = :is_default", Category.class);
		query.setParameter("is_default", 0); // N is 0
		List<Category> list = query.getResultList();
		
		// confirm
		Mark resultMark = list.get(0).getIsDefault();
		assertThat(Mark.N, is(resultMark));
		
	}
}
