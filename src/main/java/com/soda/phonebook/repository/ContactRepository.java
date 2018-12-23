package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	
	@Query("SELECT c FROM Contact c WHERE c.user = :user")
	List<Contact> findAllByUser(@Param("user") User user);
}
