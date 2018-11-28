package com.soda.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.repository.InfoRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class InfoService<T extends Info> {
	
	@Autowired
	private InfoRepository<T> infoRepository;
	
	public List<T> findByCategory(Category category){
		return infoRepository.findByCategory(category);
	}
	
	@SuppressWarnings("hiding")
	public <T extends Info> T save(T info) {
		return infoRepository.save(info);
	}
}
