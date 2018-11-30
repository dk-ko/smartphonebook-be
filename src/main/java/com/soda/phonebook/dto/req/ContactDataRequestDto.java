package com.soda.phonebook.dto.req;

public interface ContactDataRequestDto {
	
	<T extends CategoryRequestDto> T getCategory();

}
