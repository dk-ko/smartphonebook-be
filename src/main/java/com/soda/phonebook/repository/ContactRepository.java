package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
