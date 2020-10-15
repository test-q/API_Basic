package com.rest.api.NonBDD;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetCall_Test {
	RequestSpecification request;
	Response response;
	JsonPath js;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	
	@Test
	public void getUses() {
		request = RestAssured.given().baseUri(domainUrl);
		response = request.get(serviceUrl);
		System.out.println("Response: " + response.prettyPrint());
		
		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(200, response.getStatusCode());
		
		System.out.println("Time: " + response.getTime());
		System.out.println("Header: " + response.getHeader("Server"));
		Assert.assertEquals("nginx", response.getHeader("Server"));
		
		js = response.jsonPath();
		System.out.println("Body: " + js.get("meta"));
		
	}
	
	@Test
	public void getUses_WithQueryParam() {
		request = RestAssured.given().baseUri(domainUrl).queryParam("name", "Naveen Asan").queryParam("gender", "Male");
		response = request.get(serviceUrl);
		
		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(200, response.getStatusCode());
		
		System.out.println("Time: " + response.getTime());
		System.out.println("Header: " + response.getHeader("Server"));
		Assert.assertEquals("nginx", response.getHeader("Server"));
		
		js = response.jsonPath();
		System.out.println("Body: " + js.get("meta"));
		
	}
	
	@Test
	public void getUses_WithQueryParam_WithHashMap() {
		Map<String, String> userParam = new HashMap<String, String>();
		userParam.put("name", "Naveen Asan");
		userParam.put("gender", "Male");
		
		request = RestAssured.given().baseUri(domainUrl).queryParams(userParam);
		response = request.get(serviceUrl);
		
		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(200, response.getStatusCode());
		
		System.out.println("Time: " + response.getTime());
		System.out.println("Header: " + response.getHeader("Server"));
		Assert.assertEquals("nginx", response.getHeader("Server"));
		
		js = response.jsonPath();
		System.out.println("Body: " + js.get("meta"));
		
	}


}
