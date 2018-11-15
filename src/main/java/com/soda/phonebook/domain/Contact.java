package com.soda.phonebook.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Digit> digits;
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Info> infoes;
	
	@ManyToMany(mappedBy="contacts")
	private Set<Tag> tags = new HashSet<Tag>();
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	@Builder
	public Contact(User user,String name, String memo, byte[] photo) {
		this.user = user;
		this.name = name;
		this.memo = memo;
		this.photo = photo;
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
}
