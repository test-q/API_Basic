package com.rest.api.post;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class POST_Seralization_Test {
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	String domainURL = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	
	
	@Test
	public void createUser_Seralization_POJO_Test() {
		String userJson = null;
		users_POJO userData = new users_POJO("Vinod", "vinod@gmail.com", "Male", "Active");
		
		//convert pojo to json
		ObjectMapper mapper = new ObjectMapper();
		try {
			userJson = mapper.writeValueAsString(userData);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(userJson);
		
		request = given().baseUri(domainURL).contentType(ContentType.JSON)
				.header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655").body(userJson);
		
		response = request.when().post(serviceUrl);
		
		body = response.getBody();
		System.out.println("Body: " + body.prettyPrint());
		
		response.then().statusCode(200);
		
		response.then().body("data.name", equalTo(userData.getName()));
		response.then().body("data.status", equalTo(userData.getStatus()));
	}
		
		
//		@Test
//		public void createUser_DeSeralization_POJO_Test() {
//			String userJson = null;
//			users userData = new users("Vinod123", "vinod123@gmail.com", "Male", "Active");
//			System.out.println("UserData: " + userData);
//			
//			//convert pojo to json
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				userJson = mapper.writeValueAsString(userData);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//			System.out.println(userJson);
//			
//			request = given().baseUri(domainURL).contentType(ContentType.JSON)
//					.header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655").body(userJson);
//			
//			userData = request.when().post(serviceUrl).as(users.class);
//			System.out.println("Final User data: " +userData);
//			
//			
//		}
		
			

	



	
	

}
