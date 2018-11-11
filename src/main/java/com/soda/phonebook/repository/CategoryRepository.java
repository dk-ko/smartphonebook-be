package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
