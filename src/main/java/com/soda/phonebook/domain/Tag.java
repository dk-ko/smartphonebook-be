package com.soda.phonebook.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
	@OneToMany(mappedBy="tag", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<TagContact> tagContacts; 
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
