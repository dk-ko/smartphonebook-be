package com.soda.phonebook.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	@Query("SELECT c FROM Category c WHERE c.user = :user AND c.type = :type")
	Set<Category> findByType(@Param("user") User user, @Param("type") DataType type);
}
