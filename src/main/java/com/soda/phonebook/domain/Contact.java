package com.soda.phonebook.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", 
				foreignKey = @ForeignKey(name="fk_contact_user"),
				nullable=false)
	private User user;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="memo")
	private String memo = null;
	
	@Lob
	@Column(name="photo")
	private byte[] photo = null;
	
	@JsonIgnore
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	private List<Digit> digits = new ArrayList<Digit>();
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Info> infoes = new ArrayList<Info>();
	
	@ManyToMany(mappedBy="contacts")
	private Set<Tag> tags = new HashSet<Tag>();
	
	@Convert(converter = ContactTypeAttributeConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name="contact_type", nullable=false)
	private ContactType type = ContactType.DEFAULT;
	
	@Builder
	public Contact(User user,String name, String memo, byte[] photo,
				List<Digit> digits, List<Info> infoes, Set<Tag> tags, ContactType type) {
		this.user = user;
		this.name = name;
		this.memo = memo;
		this.photo = photo;
		this.digits = digits;
		this.infoes = infoes;
		this.tags = tags;
		this.type = type;
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	public void updateTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	public void updateContactType(ContactType type) {
		this.type = type;
	}

	public void updateAllInfo(String name, String memo, byte[] photo) {
		this.name = name;
		this.memo = memo;
		this	.photo = photo;
	}
	
	public void updateName(String name) {
		this.name = name;
	}
	
	public void updateMemo(String memo) {
		this.memo = memo;
	}
	
	public void updatePhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public void updateUser(User user) {
		this.user = user;
	}
}
