package com.soda.phonebook.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.repository.TagRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class TagService {
	
	private final TagRepository tagRepository;
	private final UserService userService;
	
	public Set<Tag> findAllByContact(Long id){
		Set<Tag> findTags = tagRepository.findAllByContact(id);
		return findTags; 
	}
	
	
	public Set<TagResponseDto> findAllByUser(){
		Set<Tag> findTags = tagRepository.findByUser(userService.getCurrentUser());
		Set<TagResponseDto> dtoList = new HashSet<>();
		for(Tag tag : findTags)
			dtoList.add(new TagResponseDto(tag));
		return dtoList;
	}
	
}
