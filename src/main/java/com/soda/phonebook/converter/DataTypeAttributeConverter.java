package com.soda.phonebook.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soda.phonebook.domain.VO.DataType;

@Converter(autoApply = true)
public class DataTypeAttributeConverter implements AttributeConverter<DataType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(DataType attribute) {
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
	public DataType convertToEntityAttribute(Integer dbData) {
		switch(dbData) {
		case 0: return DataType.DIGIT;
		case 1: return DataType.URL;
		case 2: return DataType.EMAIL;
		case 3: return DataType.DATE;
		case 4: return DataType.ADDRESS;
		}
		return null;
	}
}
