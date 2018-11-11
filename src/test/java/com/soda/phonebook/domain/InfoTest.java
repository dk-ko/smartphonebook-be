package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.soda.phonebook.domain.VO.Type;
import com.soda.phonebook.domain.info.Address;

public class InfoTest {

	@Test
	public void testAddressCreate() {
		User user = MockEntity.mock(User.class, 1l);
		user.updateName("고다경");
		
		Category c1 = Category.builder()
				.name("집")
				.type(Type.ADDRESS)
				.user(user).build();
		
		Category c2 = Category.builder()
				.name("회사")
				.type(Type.ADDRESS)
				.user(user).build();
		
		Info address = Address.builder()
				.contents("서울 구로구 항동")
				.category(c2)
				.build();
		
		assertThat(address.getContents(), is("서울 구로구 항동"));
		assertThat(address.getCategory().getName(), is("회사"));
		assertThat(address.getCategory().getType(), is(Type.ADDRESS));
	}

}
