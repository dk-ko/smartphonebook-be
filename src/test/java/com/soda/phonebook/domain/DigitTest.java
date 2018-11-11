package com.soda.phonebook.domain;

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

import com.soda.phonebook.domain.VO.Mark;
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
	private Digit digit;
	
	
	@Before
	public void createNumber() {
		numbers = Numbers.builder()
				.first("010")
				.second("1234")
				.third("5678").build();
		
		digit = Digit.builder()
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
	
	@Test
	public void testDigitCreate() {
		
		Digit digit = Digit.builder()
				.representation(Mark.Y)
				.numbers(numbers).build();
		
		assertThat(digit.getNumbers().getFirst(),is("010"));
		assertThat(digit.getRepresentation(),is(Mark.Y));
		
	}
	
	@Test
	public void testDigitUpdatePhoneNumber() {
		
		Digit digit = MockEntity.mock(Digit.class, 1l);
		digit.updateNumbers(numbers);
		digit.updateRepresentation(Mark.N);
		
		digit.updateNumbers(Numbers.builder()
				.first("010")
				.second("0987")
				.third("6543").build());
		
		assertThat(digit.getId(), is(1l));
		assertThat(digit.getNumbers().getThird(),is("6543"));
	}

}
