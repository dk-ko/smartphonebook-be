package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

}
