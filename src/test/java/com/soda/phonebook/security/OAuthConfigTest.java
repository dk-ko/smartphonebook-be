package com.soda.phonebook.security;

import org.junit.Before;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(
        properties = "spring.config.location=classpath:/google.yml")
public class OAuthConfigTest {

	@Before
    public void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8085;
    }
	
	@Test
    public void test_로그인시도시_google인증창 () throws Exception {
        given()
                .when()
                    .redirects().follow(false) 
                    .get("/login")
                .then()
                    .statusCode(302)
                    .header("Location", containsString("https://accounts.google.com/o/oauth2/auth"));
    }
}
