package com.rest.api.Hamcrest;

import org.hamcrest.Matcher;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class AssertionWithoutAnd {
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	String bodyDataAsString;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	JsonPath jPath;
	int total, pages, page, limit;
	
	
	//JSON response in object that means when you want to validate single user data
	@Test
	public void responseIn_Object() {
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		
		System.out.println("Body: " +body.prettyPrint());
		
		total = response.jsonPath().get("meta.pagination.total");
		pages = response.jsonPath().get("meta.pagination.pages");
		page = response.jsonPath().get("meta.pagination.page");
		limit = response.jsonPath().get("meta.pagination.limit");
		
		//Assertion
		assertThat(new Object[] {total,pages,page,limit} , is(new Object[] {1703, 86, 1, 20}));
		
	}
	
	
	//JSON response in Array that means when you want to validate multiple users data
	@Test
	public void responseIn_Array() {
		
		List<Integer> id;
		List<String> name, email;
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		
		System.out.println("Response Body: " + "\n\n" + body.prettyPrint() + "\n");
		
		//I am checking data for first 3 users
		id = new ArrayList<Integer>();
		id = response.jsonPath().get("data.id");
		
		name = new ArrayList<String>();
		name = response.jsonPath().get("data.name");
		
		email= new ArrayList<String>();
		email = response.jsonPath().get("data.email");
		
		assertThat((new Object[]{id.get(0), name.get(0), email.get(0) , 
				                 id.get(1), name.get(1), email.get(1),
				                 id.get(2), name.get(2), email.get(2)}),
			   is(new Object[]  { 4,       "Akshita Naik", "naik_akshita@powlowski-emard.name",
						          5,       "Bala Singh",    "singh_bala@swaniawski.biz",
						          6,       "Sanjay Embranthiri", "embranthiri_sanjay@kuphal.info"}));
		
	
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
