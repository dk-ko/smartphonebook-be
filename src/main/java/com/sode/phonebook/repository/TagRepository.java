package com.sode.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sode.phonebook.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{

}
