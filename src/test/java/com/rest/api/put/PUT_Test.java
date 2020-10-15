package com.rest.api.put;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PUT_Test {
	// Post        >> Put         >> GET
	// Create Data >> Update Data >> Check Updated data available or not
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	
	@Test
	public void updateUser_Put_Test() {
		
		//Create User - Using POST CALL
		GoRest_CreateUser_POJO user = new GoRest_CreateUser_POJO("Sneha" , "sneha46@gmail.com", "Female", "Inactive");
		
		//Covert POJO to JSON payload
		String userPayload = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			userPayload = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Payload For Create User: " + userPayload);
		
		request = given().baseUri(domainUrl)
				 .contentType(ContentType.JSON)
				 .header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655")
				 .body(userPayload);
		
		response = request.when().log().all().post(serviceUrl);
		
		response.then().assertThat().statusCode(200);
		//response.then().assertThat().contentType(ContentType.JSON);
		
		int userId = response.jsonPath().get("data.id");
		System.out.println("User Id Created: " + userId);
		System.out.println("*********************************************");
		
		//Update User -- PUT CALL (Updating email and status)
		user.setEmail("sneha.wani456@gmail.com");
		user.setStatus("Active");
		
		String updatedPayload = null;
		try {
			updatedPayload = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		request = given().baseUri(domainUrl)
				  .contentType(ContentType.JSON)
				  .header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655")
				  .body(updatedPayload);
		
		response = request.when().log().all().put(serviceUrl+"/"+userId);
		
		response.then().assertThat().statusCode(200);
		String name = response.jsonPath().getString("data.name");
		String email = response.jsonPath().get("data.email");
		String gender = response.jsonPath().get("data.gender");
		String status = response.jsonPath().get("data.status"); 
		
		assertThat(new Object[] {name,email,gender,status}, is(new Object[] {user.getName(),user.getEmail(), user.getGender(), user.getStatus()}));
		
		System.out.println("******************************************************");
		
		//Apply Get Call to check updated data available or not
		
		request = given().baseUri(domainUrl).queryParam("id", userId)
				  .header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655");
		
		response = request.when().log().all().get(serviceUrl);
		
		response.then().assertThat().statusCode(200);
		
		response.then().assertThat().body("data[0].id", equalTo(userId));
		
		
		
		
				
		
		
		
		
		
		
	}
	
	

	
	
	
}
