package com.soda.phonebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soda.phonebook.domain.Info;

public interface InfoRepository<T extends Info> extends JpaRepository<T, Long>{
	Optional<T> findById(Long id);
}
