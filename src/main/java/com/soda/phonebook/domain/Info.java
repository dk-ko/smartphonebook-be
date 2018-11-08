package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="info")
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
public abstract class Info extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="contact_id")
	private Contact contact;
	
	@Column(name="contents", nullable=false)
	private String contents;
	
	@ManyToOne
	@JoinColumn(name="data_type_id")
	private DataType dataType;
	
	public Info(String contents) {
		this.contents = contents;
	}
}
