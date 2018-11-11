package com.soda.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.Digit;

public interface DigitRepository extends JpaRepository<Digit, Long>{

}
