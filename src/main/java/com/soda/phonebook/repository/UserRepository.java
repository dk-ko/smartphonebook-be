package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByName(String name);
	
    @Query("select distinct u from User u where u.email=:email")
    User findByEmail(@Param("email") String email);
}
