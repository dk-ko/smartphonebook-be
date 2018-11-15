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

import com.soda.phonebook.converter.TypeAttributeConverter;
//import com.soda.phonebook.converter.TypeAttributeConverter;
import com.soda.phonebook.domain.VO.Type;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Convert(converter = TypeAttributeConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name="type", nullable=false)
	private Type type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", 
				foreignKey = @ForeignKey(name="fk_category_user"),
				nullable = false)
	private User user;
	
	@Builder
	public Category(String name, Type type, User user) {
		this.name = name;
		this.type = type;
		this.user = user;
	}
}
