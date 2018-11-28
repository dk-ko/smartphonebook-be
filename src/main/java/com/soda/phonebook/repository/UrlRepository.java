package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.domain.info.Url;

public interface UrlRepository extends InfoRepository<Url>{
	@Query("SELECT u FROM Url u JOIN FETCH u.contact c WHERE c.id = :id")
	List<Info> findAllByContact(@Param("id") Long id);
}
