package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.info.Info;

public interface InfoRepository<T extends Info> extends JpaRepository<T, Long>{
	@Query("SELECT i FROM Info i JOIN FETCH i.contact c WHERE c.id = :id")
	List<Info> findAllByContact(@Param("id") Long id);
}
