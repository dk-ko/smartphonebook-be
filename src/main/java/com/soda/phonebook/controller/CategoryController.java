package com.soda.phonebook.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.dto.req.CategorySaveRequestDto;
import com.soda.phonebook.dto.res.CategoryResponseDto;
import com.soda.phonebook.service.CategoryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryController {
	
	private CategoryService categoryService;
	
	@GetMapping("/{type}")
	@ResponseStatus(value = HttpStatus.OK)
	public Set<CategoryResponseDto> getCategoriesByDigit(@PathVariable DataType type) {
		log.info("잘못된 값 입력 시 에러 발생 체크");
		return categoryService.findByType(type);
	}
	
	@PostMapping("/{type}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public CategoryResponseDto createCategory(@PathVariable DataType type, @RequestBody CategorySaveRequestDto dto) {
		return categoryService.create(dto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable final Long id) {
		categoryService.delete(id);
	}
}
