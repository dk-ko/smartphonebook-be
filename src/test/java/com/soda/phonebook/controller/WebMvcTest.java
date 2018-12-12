package com.soda.phonebook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest(controllers = WebRestController.class)
public class WebMvcTest {
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void test_샘플() throws Exception {
		this.mvc.perform(get("/hello").accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string("HelloWorld !!"));
	}
}
