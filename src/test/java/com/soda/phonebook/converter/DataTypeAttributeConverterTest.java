package com.soda.phonebook.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
//@DataJpaTest
public class DataTypeAttributeConverterTest {
	
	@PersistenceContext
    private EntityManager em;
	
	private List<Category> categoryList = new ArrayList<Category>();
	
	@Before
	public void setUp() {
		User user = User.builder()
					.name("유저1")
					.build();
		em.persist(user);
		em.flush();
		em.clear();
		
		categoryList.add(Category.builder().name("집").type(DataType.ADDRESS).user(user).build());
		categoryList.add(Category.builder().name("회사").type(DataType.ADDRESS).user(user).build());
		categoryList.add(Category.builder().name("기타").type(DataType.ADDRESS).user(user).build());
		categoryList.add(Category.builder().name("집").type(DataType.DIGIT).user(user).build());
		categoryList.add(Category.builder().name("회사").type(DataType.DIGIT).user(user).build());
		categoryList.add(Category.builder().name("기타").type(DataType.DIGIT).user(user).build());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void test_dataType_converter() {
		Iterator<Category> it = categoryList.iterator();
		while(it.hasNext()) {
			Category c = it.next();
			em.persist(c);
		}
		em.flush();
		em.clear();
		
		// native query로 조회 
		Query query = em.createNativeQuery("select * from category where data_type = :data_type", Category.class);
		query.setParameter("data_type", 4); // ADDRESS is 4
		List<Category> list = query.getResultList();
		
		// confirm 
		DataType resultType = list.get(0).getType();
		assertThat(DataType.ADDRESS, is(resultType));
	}

}
