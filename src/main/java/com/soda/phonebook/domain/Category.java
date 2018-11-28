package com.soda.phonebook.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.converter.DataTypeAttributeConverter;
import com.soda.phonebook.converter.MarkAttributeConverter;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;

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
	
	@Convert(converter = DataTypeAttributeConverter.class)
	@Column(name="data_type", nullable=false)
	private DataType type;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", 
				foreignKey = @ForeignKey(name="fk_category_user"),
				nullable = false)
	private User user;
	
	@Convert(converter = MarkAttributeConverter.class)
	@Column(name="default", nullable=false)
	private Mark isDefault = Mark.N;
	
	@Builder
	public Category(String name, DataType type, User user, Mark isDefault) {
		this.name = name;
		this.type = type;
		this.user = user;
		this.isDefault = Optional.ofNullable(isDefault).orElse(this.isDefault);
	}
	
}
