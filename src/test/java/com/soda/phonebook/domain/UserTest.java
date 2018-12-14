package com.soda.phonebook.domain;

import com.soda.phonebook.config.JpaAuditConfiguration;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.repository.CategoryRepository;
import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.TagRepository;
import com.soda.phonebook.repository.UserRepository;

@Import(value = JpaAuditConfiguration.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	private User user, sUser;
	private Contact contact;
	private Tag tag;
	private Category category1, category2;
	
	@Before
	public void setUp() throws Exception{
		// test 1
		user = User.builder().name("user1").build();
		
		// test 2
		// user - contact, tag, category, favorite
		sUser = userRepository.save(user);
		
		tag = Tag.builder().name("태그저장").user(sUser).build();
		em.persist(tag);
		
		category1 = Category.builder()
				.user(sUser)
				.type(DataType.DIGIT)
				.name("개인").build();
		category2 = Category.builder()
				.user(sUser)
				.type(DataType.ADDRESS)
				.name("집").build();
		em.persist(category1);
		em.persist(category2);
		
		contact = Contact.builder()
				.name("테스트연락처")
				.user(sUser).build();
		em.persist(contact);
		
		sUser.getContacts().add(contact);
		sUser.getTags().add(tag);
		sUser.getCategories().add(category1);
		sUser.getCategories().add(category2);
		sUser.getFavorites().add(contact);
		
		em.persist(sUser);
		em.flush();
	}
	
	@Test
	public void testUserCreate() {
		User savedUser = userRepository.save(user);
		User findedUser = userRepository.findAll().get(0);
		
		assertThat(savedUser.getName(), is("user1"));
		assertThat(findedUser.getName(), is("user1"));
		
	}
	
	@Test
	@Transactional
	public void test_User삭제시_Cascade적용() {
		// user - contact, tag, category, favorite		
		assertNotNull(categoryRepository.findById(1l));
		assertNotNull(categoryRepository.findById(2l));
		assertNotNull(contactRepository.findById(1l));
		assertNotNull(tagRepository.findById(1l));
		
		em.remove(sUser);
		em.flush();
		
		assertThat(categoryRepository.findById(1l),is(Optional.empty()));
		assertNull(em.find(Category.class, 2l));
		assertNull(em.find(Contact.class, 1l));
		assertNull(em.find(Tag.class, 1l));
		
	}
}
