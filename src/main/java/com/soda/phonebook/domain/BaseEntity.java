package com.soda.phonebook.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(updatable = false)
	protected LocalDateTime createdAt;
	
	@Column
	protected LocalDateTime updateAt;
	
	@PrePersist
	protected void onPersist() {
		this.createdAt = this.updateAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updateAt = LocalDateTime.now();
	}
}
