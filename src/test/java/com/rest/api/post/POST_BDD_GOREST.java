package com.rest.api.post;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import java.io.File;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class POST_BDD_GOREST {
	
	RequestSpecification request;
	Response response;
	ResponseBody  body;
	String domainURL = "https://gorest.co.in";
	String serviceURL = "/public-api/users";
	

	@Test
	public void post_PayloadWithFile() {
		request = given().baseUri(domainURL).auth().oauth2("0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655")
				  .header("Content-Type", "application/json")
				  .body(new File("src\\test\\java\\dataFiles\\CreateNewUser.json"));
		
		response = request.when().post(serviceURL);
		
		String name = response.jsonPath().get("data.name");
		String email = response.jsonPath().get("data.email");
		String gender = response.jsonPath().get("data.gender");
		
		response.then().assertThat().statusCode(200);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Body: " +"\n\n" + response.prettyPrint());
		
		assertThat(new Object[] {name,email,gender} , is(new Object[] {"sharvi", "sharvigurav@gmail.com", "Female"}));
		
	}

}
