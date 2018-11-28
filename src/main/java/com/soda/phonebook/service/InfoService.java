package com.soda.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.repository.InfoRepository;

@Service
public class InfoService {
	
	@Autowired
	private InfoRepository<Info> infoRepository;
	
	List<Info> findByCategory(Category category){
		return infoRepository.findByCategory(category);
	}
}
