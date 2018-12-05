package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.repository.DigitRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DigitService {
	
	private final DigitRepository digitRepository;
	
	public List<Digit> findAllByContact(Long id){
		List<Digit> findDigits = digitRepository.findAllByContact(id);
		return (findDigits.isEmpty() || findDigits.equals(null)) 
				? new ArrayList<Digit>() : findDigits;
	}

	public List<Digit> findByCategory(Category category){
		return digitRepository.findByCategory(category);
	}
	
	public Digit save(Digit digit) {
		return digitRepository.save(digit);
	}
	
	public Digit findById(Long id) {
		return digitRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id"));
	}
}
