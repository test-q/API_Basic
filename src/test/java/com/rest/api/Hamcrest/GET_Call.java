package com.rest.api.Hamcrest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class GET_Call {
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	String bodyDataAsString;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	JsonPath jPath;
	
	@Test
	public void get_AllUsers() {
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		
		System.out.println("Status Code: " + response.getStatusCode());
		response.then().assertThat().statusCode(200);
		
		jPath = response.jsonPath();
		
		String id = jPath.getString("data.id[0]");
		System.out.println("ID: " +id);
		String name = jPath.getString("data.name[0]");
		System.out.println("NAME: " +name );
		System.out.println("TOTAL PAGES: " + response.jsonPath().getString("meta.pagination.total"));
		
		System.out.println("Response Body: " + "\n\n" + response.prettyPrint() + "\n");
		
		//Apply Hamcrest Methods
		response.then().assertThat().body("meta.pagination.pages", equalTo(84));
		response.then().assertThat().body("data.id[0]", equalTo(1));
		response.then().assertThat().body("data.name[0]", equalTo("Brahma Sinha"));
		response.then().assertThat().body("data.id", hasSize(20));
		response.then().assertThat().body("data.id", everyItem(greaterThan(0)));
		response.then().assertThat().body("data.id", everyItem(lessThanOrEqualTo(30)));
		response.then().assertThat().body("data.id", contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
		response.then().assertThat().body("data.id", notNullValue());
		//response.then().assertThat().body("data.id", nullValue()); //When you expect null value	
	}
	

}
