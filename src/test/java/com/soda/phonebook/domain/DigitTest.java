package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.domain.VO.Numbers;
import com.soda.phonebook.repository.DigitRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DigitTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DigitRepository digitRepository;
	
	private Numbers numbers;
	
	@Before
	public void createNumber() {
		numbers = Numbers.builder()
				.first("010")
				.second("1234")
				.third("5678").build();
	}
	
	
	@Test
	public void testDigitUpdatePhoneNumber() {
		
		Digit digit = MockEntity.mock(Digit.class, 1l);
		digit.updateNumbers(numbers);
		
		digit.updateNumbers(Numbers.builder()
				.first("010")
				.second("0987")
				.third("6543").build());
		
		assertThat(digit.getId(), is(1l));
		assertThat(digit.getNumbers().getThird(),is("6543"));
	}

}
