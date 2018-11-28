package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.info.Date;
import com.soda.phonebook.domain.info.Info;

public interface DateRepository extends InfoRepository<Date>{
	@Query("SELECT d FROM Date d JOIN FETCH d.contact c WHERE c.id = :id")
	List<Info> findAllByContact(@Param("id") Long id);
}
