package com.soda.phonebook.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.repository.CategoryRepository;
import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataTypeAttributeConverterTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	@Before
	public void setUp() {
		User user = User.builder()
					.name("테스트유저")
					.build();
		
		User savedUser = userRepository.save(user);
		
		categoryList.add(Category.builder().name("집").type(DataType.DIGIT).user(savedUser).build());
		categoryList.add(Category.builder().name("회사").type(DataType.DIGIT).user(savedUser).build());
		categoryList.add(Category.builder().name("기타").type(DataType.DIGIT).user(savedUser).build());
		categoryList.add(Category.builder().name("집").type(DataType.ADDRESS).user(savedUser).build());
		categoryList.add(Category.builder().name("회사").type(DataType.ADDRESS).user(savedUser).build());
		categoryList.add(Category.builder().name("기타").type(DataType.ADDRESS).user(savedUser).build());
	}
	
	@Test
	public void test_type_converter() {
		
		categoryRepository.save(categoryList);
		
		// native query
		Query query = em.createNativeQuery("select * from category where type = 0", Category.class);
		assertThat(query.getFirstResult(), is(0)); // DIGIT is 0.
		
		// confirm 
		Category savedCategory = em.find(Category.class, 1l);
		assertThat(savedCategory.getType(),is(DataType.DIGIT)); 
	}

}
