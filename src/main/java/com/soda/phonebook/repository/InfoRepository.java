package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.info.Info;

@Repository
public interface InfoRepository<T extends Info> extends JpaRepository<T, Long>{
	@Query("SELECT i FROM Info i WHERE i.category = :category")
	List<T> findByCategory(@Param("category") Category category);
}
