package com.soda.phonebook.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soda.phonebook.domain.VO.Type;

@Converter
public class TypeAttributeConverter implements AttributeConverter<Type, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Type attribute) {
		switch(attribute) {
			case DIGIT: return 0;
			case URL: return 1;
			case EMAIL: return 2;
			case DATE: return 3;
			case ADDRESS: return 4;
		}
		return -1;
	}

	@Override
	public Type convertToEntityAttribute(Integer dbData) {
		switch(dbData) {
		case 0: return Type.DIGIT;
		case 1: return Type.URL;
		case 2: return Type.EMAIL;
		case 3: return Type.DATE;
		case 4: return Type.ADDRESS;
		}
		return null;
	}
}
