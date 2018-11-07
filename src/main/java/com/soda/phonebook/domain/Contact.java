package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.domain.VO.Mark;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contact")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Contact extends BaseEntity{
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

	@ManyToOne
	@JoinColumn(name ="user_id")
	private User user;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="memo")
	private String memo = null;
	
	@Lob
	@Column(name="photo")
	private byte[] photo = null;
	
	@Column(name="favorite", nullable=false)
	@Enumerated(EnumType.STRING)
	private Mark favorite = Mark.N;
	
	// 이 밑부터 digits. infoes, tags
	
	@Builder
	public Contact(Long id, String name, String memo, byte[] photo, Mark favorite) {
		this.id = id;
		this.name = name;
		this.memo = memo;
		this.photo = photo;
		this.favorite = favorite;
	}

	public void updateAllInfo(String name, String memo, byte[] photo, Mark favorite) {
		this.name = name;
		this.memo = memo;
		this	.photo = photo;
		this.favorite = favorite;
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
	
	public void updateFavorite(Mark favorite) {
		this.favorite = favorite;
	}
}
