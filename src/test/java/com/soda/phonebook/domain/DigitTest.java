package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.Numbers;

public class DigitTest {
	
	private static Numbers numbers;
	
	@Before
	public void createNumber() {
		numbers = Numbers.builder()
				.first("010")
				.second("1234")
				.third("5678").build();
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
