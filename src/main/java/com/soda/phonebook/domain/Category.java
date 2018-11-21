package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
//import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.converter.DataTypeAttributeConverter;
//import com.soda.phonebook.converter.TypeAttributeConverter;
import com.soda.phonebook.domain.VO.DataType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Convert(converter = DataTypeAttributeConverter.class, attributeName = "data_type")
@Entity
@Table(name="category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
//	@Convert(converter = DataTypeAttributeConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name="data_type", nullable=false)
	private DataType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", 
				foreignKey = @ForeignKey(name="fk_category_user"),
				nullable = false)
	private User user;
	
	@Builder
	public Category(String name, DataType type, User user) {
		this.name = name;
		this.type = type;
		this.user = user;
	}
}
