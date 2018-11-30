package com.soda.phonebook.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	
	@Query("SELECT DISTINCT t FROM Contact c LEFT JOIN c.tags t WHERE c.id = :id")
	Set<Tag> findAllByContact(@Param("id") Long id);
	
	Set<Tag> findByUser(User user);
}
