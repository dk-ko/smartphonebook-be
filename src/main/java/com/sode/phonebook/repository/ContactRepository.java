package com.sode.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sode.phonebook.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

}
