package com.soda.phonebook.domain;

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

import com.soda.phonebook.domain.VO.Type;
import com.soda.phonebook.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryTest {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	@Before
	public void setUp() {
		categoryList.add(Category.builder().name("집").type(Type.DIGIT).build());
		categoryList.add(Category.builder().name("회사").type(Type.DIGIT).build());
		categoryList.add(Category.builder().name("기타").type(Type.DIGIT).build());
		categoryList.add(Category.builder().name("집").type(Type.ADDRESS).build());
		categoryList.add(Category.builder().name("회사").type(Type.ADDRESS).build());
		categoryList.add(Category.builder().name("기타").type(Type.ADDRESS).build());
	}
	
	@Test
	public void test_type_converter() {
		
		categoryRepository.save(categoryList);
		
		// native query
		Query query = em.createNativeQuery("select * from category where type = 0", Category.class);
		assertThat(query.getFirstResult(), is(0)); // DIGIT is 0.
		
		// confirm 
		Category savedCategory = em.find(Category.class, 1l);
		System.out.println(savedCategory.getType());
		assertThat(savedCategory.getType(),is(Type.DIGIT)); 
	}

}
