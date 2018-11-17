package com.soda.phonebook.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@CreatedDate
	protected LocalDateTime createDate;
//	@Column(updatable = false)
//	protected LocalDateTime createdAt;
	
	@LastModifiedDate
	protected LocalDateTime modifiedDate;
//	@Column
//	protected LocalDateTime updateAt;
	
//	@PrePersist
//	protected void onPersist() {
//		this.createdAt = this.updateAt = LocalDateTime.now();
//	}
//	
//	@PreUpdate
//	protected void onUpdate() {
//		this.updateAt = LocalDateTime.now();
//	}
}
