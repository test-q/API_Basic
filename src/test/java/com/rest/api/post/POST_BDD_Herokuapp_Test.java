package com.rest.api.post;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class POST_BDD_Herokuapp_Test {
	
	RequestSpecification request;
	Response response;
	ResponseBody  body;
	String domainURL = "https://restful-booker.herokuapp.com";
	String serviceURL = "/auth";
	JsonPath jPath;
	
	@Test
	public void post_jsonObject() {
	
		JSONObject payload = new JSONObject();
		payload.put("username", "admin");
		payload.put("password", "password123");
		
		request = given().baseUri(domainURL).header("Content-Type", "application/json").body(payload.toJSONString());
		response = request.when().post(serviceURL);
		
		String tokenValue = response.jsonPath().getString("token");
		System.out.println("API Token: " + tokenValue);
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("token", notNullValue());				
	}
	
	@Test
	public void post_withoutJsonObject() {
		String payload = "{\"username\" : \"admin\", \"password\" : \"password123\"}";
		request = given().baseUri(domainURL).header("Content-Type", "application/json").body(payload);
		response = request.when().post(serviceURL);
		
		String tokenValue = response.jsonPath().getString("token");
		System.out.println("API Token: " + tokenValue);
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("token", notNullValue());			
	}
	
	@Test
	public void post_PayloadWithFile() {
		request = given().baseUri(domainURL).header("Content-Type", "application/json").body(new File("src/test/java/dataFiles/CreateToken.json"));
		response = request.when().post(serviceURL);
		
		String tokenValue = response.jsonPath().getString("token");
        System.out.println("API Token: " + tokenValue);
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("token", notNullValue());	
	}
	
	
	
	
	

}
