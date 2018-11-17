package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByName(String name);
}
