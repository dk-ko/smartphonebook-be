package com.soda.phonebook.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soda.phonebook.domain.VO.ContactType;

@Converter(autoApply = true)
public class ContactTypeAttributeConverter implements AttributeConverter<ContactType, Integer>{

	@Override
	public Integer convertToDatabaseColumn(ContactType attribute) {
		switch(attribute) {
		case DEFAULT: return 0;
		case ME: return 1;
		case FAVORITED: return 2;
	}
		return -1;
	}

	@Override
	public ContactType convertToEntityAttribute(Integer dbData) {
		switch(dbData) {
		case 0: return ContactType.DEFAULT;
		case 1: return ContactType.ME;
		case 2: return ContactType.FAVORITED;
		}
		return null;
	}
	
}
