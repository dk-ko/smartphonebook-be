package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soda.phonebook.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	
}
