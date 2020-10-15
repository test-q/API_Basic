package com.rest.api.delete;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.put.GoRest_CreateUser_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Delete_Test {
	RequestSpecification request;
	Response response;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	
	@Test
	public void delete_User_Test() {
		// Post        >> Delete         >> GET
		// Create Data >> Delete Data    >> Check data deleted or not
		
		GoRest_CreateUser_POJO userPojo = new GoRest_CreateUser_POJO("Vibha", "vibha123@gmail.com", "Female", "Active" );
		
		//Convert Pojo object into JSON Payload >> JACKSON DATABIND
		String userPayload = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			userPayload = mapper.writeValueAsString(userPojo);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Create New User Payload: " +userPayload);
		
		//Create user >> POST CALL
		request = given().baseUri(domainUrl).contentType(ContentType.JSON)
				  .header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655")
				  .body(userPayload);
		
		response = request.when().log().all().post(serviceUrl);
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("data.email", equalTo(userPojo.getEmail()));
		int userId = response.jsonPath().get("data.id");
		System.out.println("User ID: " + userId);
		System.out.println("*******************************************************");
		
		//DELETE IS NOT WORKING IN GOREST
		//Delete User >> DELETE CALL
		request = given().baseUri(domainUrl).header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655");
		
		response = request.when().log().all().delete(serviceUrl + "/" + userId);
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("meta", nullValue());
		
		//Try to get user ->User should not available in get list
		
		
		
	}

}
