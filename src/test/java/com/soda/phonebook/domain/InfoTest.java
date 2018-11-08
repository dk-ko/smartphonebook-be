package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.soda.phonebook.domain.info.Address;

public class InfoTest {

	@Test
	public void testAddressCreate() {
		Info address = Address.builder().contents("seoul").build();
		
		assertThat(address.getContents(), is("seoul"));
		// assertThat(address.getType(), is(Type.ADDRESS));
	}
	
	// DTYPE으로 조회하는 TEST

}
