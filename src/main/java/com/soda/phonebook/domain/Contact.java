package com.soda.phonebook.domain;

import java.util.HashSet;
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
	
	@Convert(converter = ContactTypeAttributeConverter.class)
	@Column(name="contact_type", nullable=false)
	private ContactType type;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="memo")
	private String memo = null;
	
	@Lob
	@Column(name="photo")
	private byte[] photo = null;
	
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL
				,fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Digit> digits = new HashSet<>();
	
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL
				,fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Info> infoes = new HashSet<>();
	
	@ManyToMany(mappedBy="contacts", fetch = FetchType.EAGER)
	private Set<Tag> tags = new HashSet<>();

	
	@Builder
	public Contact(User user, ContactType type, String name, String memo, byte[] photo,
				Set<Digit> digits, Set<Info> infoes, Set<Tag> tags) {
		this.user = user;
		this.type = type;
		this.name = name;
		
		this.memo = Optional.ofNullable(memo).orElse(this.memo);
		this.photo = Optional.ofNullable(photo).orElse(this.photo);
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.infoes = Optional.ofNullable(infoes).orElse(this.infoes);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	// 1:N
	public void addDigit(Digit digit) {
		this.digits.add(digit);
	}
	
	public <T extends Info> void addInfo(T info) {
		this.infoes.add(info);
	}
	
	// Join Table
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	
	// 밑에 확인 후 필요 없는 것 삭제 
	
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
