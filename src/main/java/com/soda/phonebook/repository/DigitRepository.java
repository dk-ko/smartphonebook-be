package com.soda.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soda.phonebook.domain.Digit;

public interface DigitRepository extends JpaRepository<Digit, Long>{

	@Query("SELECT d FROM Digit d JOIN FETCH d.contact c WHERE c.id = :id")
//	@Query("SELECT d FROM Digit d LEFT JOIN d.contact c WHERE c.id = :id")
	List<Digit> findAllByContact(@Param("id") Long id);
}
