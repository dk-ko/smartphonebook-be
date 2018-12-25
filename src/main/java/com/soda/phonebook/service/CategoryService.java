package com.soda.phonebook.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.common.CanNotDeleteCategory;
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.req.CategorySaveRequestDto;
import com.soda.phonebook.dto.res.CategoryResponseDto;
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
	public Set<CategoryResponseDto> findByType(DataType type, User user){
//		User user = userService.getCurrentUser();
		User findUser = userService.findByEmail(user.getEmail());
		
		Set<Category> findCategories = categoryRepository.findByType(findUser, type);
		Set<CategoryResponseDto> dtoList = new LinkedHashSet<>();
		for(Category category : findCategories)
			dtoList.add(new CategoryResponseDto(category));
		return dtoList;
	}
	
	public CategoryResponseDto create(CategorySaveRequestDto dto, User user) {
		Category category = dto.toEntity(userService.findByEmail(user.getEmail()));
		
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
	
	public void createDefaultCategory(User user) {
		Category digit1 = Category.builder().type(DataType.DIGIT).name("휴대전화").user(user).build();
		Category digit2 = Category.builder().type(DataType.DIGIT).name("집").user(user).build();
		Category digit3 = Category.builder().type(DataType.DIGIT).name("직장").user(user).build();
		Category digit4 = Category.builder().type(DataType.DIGIT).name("팩스").user(user).build();
		Category digit5 = Category.builder().type(DataType.DIGIT).name("기타").user(user).build();
		
		categoryRepository.save(digit1);
		categoryRepository.save(digit2);
		categoryRepository.save(digit3);
		categoryRepository.save(digit4);
		categoryRepository.save(digit5);
		
		Category url1 = Category.builder().type(DataType.URL).name("개인").user(user).build();
		Category url2 = Category.builder().type(DataType.URL).name("직장").user(user).build();
		Category url3 = Category.builder().type(DataType.URL).name("기타").user(user).build();
		
		categoryRepository.save(url1);
		categoryRepository.save(url2);
		categoryRepository.save(url3);
		
		Category email1 = Category.builder().type(DataType.EMAIL).name("개인").user(user).build();
		Category email2 = Category.builder().type(DataType.EMAIL).name("직장").user(user).build();
		Category email3 = Category.builder().type(DataType.EMAIL).name("기타").user(user).build();
		
		categoryRepository.save(email1);
		categoryRepository.save(email2);
		categoryRepository.save(email3);
		
		Category date1 = Category.builder().type(DataType.DATE).name("생일").user(user).build();
		Category date2 = Category.builder().type(DataType.DATE).name("기념일").user(user).build();
		Category date3 = Category.builder().type(DataType.DATE).name("기타").user(user).build();
		
		categoryRepository.save(date1);
		categoryRepository.save(date2);
		categoryRepository.save(date3);
		
		Category address1 = Category.builder().type(DataType.ADDRESS).name("집").user(user).build();
		Category address2 = Category.builder().type(DataType.ADDRESS).name("직장").user(user).build();
		Category address3 = Category.builder().type(DataType.ADDRESS).name("기타").user(user).build();
		
		categoryRepository.save(address1);
		categoryRepository.save(address2);
		categoryRepository.save(address3);
	}
}
