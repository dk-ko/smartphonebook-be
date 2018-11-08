package com.soda.phonebook.domain;

import javax.persistence.Column;
//import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import com.soda.phonebook.converter.TypeAttributeConverter;
import com.soda.phonebook.domain.VO.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="data_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DataType extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
	//@Convert(converter = TypeAttributeConverter.class)
	@Column(name="type", nullable=false)
	private Type type; // default 값 설정해야 
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}
