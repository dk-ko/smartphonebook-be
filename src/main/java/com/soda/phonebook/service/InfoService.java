package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.repository.InfoRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class InfoService {
	
	private final InfoRepository<Info> infoRepository;
	
	public List<Info> findAllByContact(Long id){
		List<Info> findInfoes = infoRepository.findAllByContact(id);
		return (findInfoes.isEmpty() || findInfoes.equals(null)) 
				? new ArrayList<Info>() : findInfoes;
	}

}
