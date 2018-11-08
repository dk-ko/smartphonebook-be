package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.PhoneNumber;

public class DigitTest {
	
	private static PhoneNumber number = PhoneNumber.builder()
			.first("010")
			.second("1234")
			.third("5678").build();
	
	@Test
	public void testDigitCreate() {
		
		Digit digit = Digit.builder()
				.representation(Mark.Y)
				.phoneNumber(number).build();
		
		assertThat(digit.getPhoneNumber().getFirst(),is("010"));
		assertThat(digit.getRepresentation(),is(Mark.Y));
		
	}
	
	@Test
	public void testDigitUpdatePhoneNumber() {
		
		Digit digit = MockEntity.mock(Digit.class, 1l);
		digit.updatePhoneNumber(number);
		digit.updateRepresentation(Mark.N);
		
		digit.updatePhoneNumber(PhoneNumber.builder()
				.first("010")
				.second("0987")
				.third("6543").build());
		
		assertThat(digit.getId(), is(1l));
		assertThat(digit.getPhoneNumber().getThird(),is("6543"));
	}

}
