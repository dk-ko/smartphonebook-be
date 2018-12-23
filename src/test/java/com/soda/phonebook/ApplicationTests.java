package com.soda.phonebook;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApplicationTests {

	@Before
	public void setup() {
		RestAssured.port = 8085;
	}
	
	@Test
	public void contextLoads() {
		assertTrue(true);
	}
	
	@Test
	public void test_default_path접근시_index호출 () throws Exception {
		given()
		.when()
			.get("/")
		.then()
			.statusCode(200)
			.contentType("text/html")
			.body(containsString("권한 관리"));
	}

}
