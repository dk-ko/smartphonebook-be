package com.soda.phonebook.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.CategorySaveRequestDto;
import com.soda.phonebook.dto.res.CategoryResponseDto;
import com.soda.phonebook.exception.CanNotDeleteCategory;
import com.soda.phonebook.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	private final UserService userService;
	private final DigitService digitService;
	private final InfoService<Info> infoService;
	
	@Transactional(readOnly = true)
	public Set<CategoryResponseDto> findByType(DataType type){
		User user = userService.getCurrentUser();
		
		Set<Category> findCategories = categoryRepository.findByType(user, type);
		Set<CategoryResponseDto> dtoList = new LinkedHashSet<>();
		for(Category category : findCategories)
			dtoList.add(new CategoryResponseDto(category));
		return dtoList;
	}
	
	public CategoryResponseDto create(CategorySaveRequestDto dto) {
		Category category = dto.toEntity(userService.getCurrentUser());
		
		return new CategoryResponseDto(categoryRepository.save(category));
	}
	
	public void delete(Long id) {
		Category findCategory = categoryRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("category find by id error"));
		
		List<Digit> digitList = digitService.findByCategory(findCategory);
		List<Info> infoList = infoService.findByCategory(findCategory);
		
		if(!digitList.isEmpty() || !infoList.isEmpty())
			throw new CanNotDeleteCategory("reference category");
		
		if(findCategory.getIsDefault().equals(Mark.N))
			categoryRepository.delete(findCategory);
		else throw new CanNotDeleteCategory("default category");
	}
	
	@Transactional(readOnly = true)
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}
}
