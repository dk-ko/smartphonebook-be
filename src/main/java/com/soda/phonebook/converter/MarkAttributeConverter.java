package com.soda.phonebook.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soda.phonebook.domain.VO.Mark;

@Converter
public class MarkAttributeConverter implements AttributeConverter<Mark, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Mark attribute) {
		switch(attribute) {
			case N: return 0;
			case Y: return 1;
		}
		return -1;
	}

	@Override
	public Mark convertToEntityAttribute(Integer dbData) {
		switch(dbData) {
			case 0: return Mark.N;
			case 1: return Mark.Y;
		}
		return null;
	}

}
