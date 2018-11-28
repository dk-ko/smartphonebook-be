package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.info.Address;
import com.soda.phonebook.domain.info.Info;

public interface AddressRepository extends InfoRepository<Address>{
	@Query("SELECT a FROM Address a JOIN FETCH a.contact c WHERE c.id = :id")
	List<Info> findAllByContact(@Param("id") Long id);
}
