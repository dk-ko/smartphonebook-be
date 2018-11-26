package com.soda.phonebook.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.repository.TagRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TagService {
	
	private final TagRepository tagRepository;
	
	public Set<Tag> findAllByContact(Long id){
		Set<Tag> findTags = tagRepository.findAllByContact(id);
		return (findTags.isEmpty() || findTags.equals(null)) 
				? new HashSet<Tag>() : findTags; 
	}
	
}
