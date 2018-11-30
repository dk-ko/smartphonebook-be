package com.soda.phonebook.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soda.phonebook.converter.ContactTypeAttributeConverter;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contact")
@AttributeOverride(name="id", column=@Column(name="contact_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Contact extends BaseEntity{
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="user_id", 
				foreignKey = @ForeignKey(name="fk_contact_user"),
				nullable=false)
	private User user;
	
	@Convert(converter = ContactTypeAttributeConverter.class)
	@Column(name="contact_type", nullable=false)
	private ContactType type;
	
	@Column(name="name", nullable=false)
	@OrderBy("name asc")
	private String name;
	
	
	@Column(name="memo")
	private String memo = null;
	
	@Lob
	@Column(name="photo")
	private byte[] photo = null;
	
	@JsonIgnore
	@OneToMany(mappedBy="contact", 
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				orphanRemoval = true)
	private List<Digit> digits = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="contact", 
				cascade = CascadeType.ALL,
				fetch = FetchType.LAZY, 
				orphanRemoval = true)
	private List<Info> infoes = new ArrayList<>();
	
	@ManyToMany(mappedBy="contacts", 
				cascade = CascadeType.PERSIST, 
				fetch = FetchType.LAZY)
	private Set<Tag> tags = new HashSet<>();

	
	@Builder
	public Contact(Long id, User user, ContactType type, String name, String memo, byte[] photo,
				List<Digit> digits, List<Info> infoes, Set<Tag> tags) {
		this.id = id;
		this.user = user;
		this.type = type;
		this.name = name;
		
		this.memo = Optional.ofNullable(memo).orElse(this.memo);
		this.photo = Optional.ofNullable(photo).orElse(this.photo);
		this.digits= Optional.ofNullable(digits).orElse(this.digits);
		this.infoes = Optional.ofNullable(infoes).orElse(this.infoes);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	public void updateContact(Contact contact) {
		this.type = contact.type;
		this.name = contact.name;
		this.memo = contact.memo;
		this.photo = contact.photo;
	}
	
	
	@Override
	public String toString() {
		String f = "\n[Contact] id: %d, user_id: %d, type: %s, name: %s, memo: %s, photo: %s, tags: %s, digits: %s, infoes: %s";
		
		return String.format(f, this.id, this.user.getId(), this.type, 
				this.name, this.memo, withPhoto(), this.tags.toString(), 
				this.digits.toString(),this.infoes.toString());
	}
	
	private String withPhoto() {
		return this.photo != null ? "exist" : "none";
	}
}
