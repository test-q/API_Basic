package com.rest.api.Hamcrest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class AssertionWithAnd {
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	String bodyDataAsString;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users";
	JsonPath jPath;
	
	@Test
	public void get_hamcret_String_Assertion() {
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		
		System.out.println("Body: " + "\n\n" + body.prettyPrint() + "\n");
		
		response.then().assertThat().statusCode(200)
		.and().assertThat().body("data.gender[0]", equalTo("Female"))
		.and().assertThat().body("data.name[0]", is("Brahma Sinha"))
		.and().assertThat().body("data.email[0]", equalToIgnoringCase("brahMa_sinha@little.co"))
		.and().assertThat().body("data.name[0]", equalToIgnoringWhiteSpace(" Brahma Sinha"))
		.and().assertThat().body("data.email[0]", startsWith("brahma"))
		.and().assertThat().body("data.name[0]", endsWith("Sinha"))
		.and().assertThat().body("data.email[0]", containsString("@"));
	}
	
	@Test
	public void get_hamcret_Object_Assertion() {
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		System.out.println("Body: " + "\n\n" + body.prettyPrint() + "\n");
		response.then().assertThat().statusCode(200)
		.and().assertThat().body("data.id[0]", notNullValue())
		.and().assertThat().body("meta.pagination", hasKey("total"))
		.and().assertThat().body("meta.pagination", hasValue(20))
		.and().assertThat().body("meta.pagination", hasEntry("pages", 86))
		.and().assertThat().body("data.id", hasItem(3))
		.and().assertThat().body("data.id", hasItems(1,2,3,4,5));
		
	}
	
	@Test
	public void get_hamcret_Numeric_Assertion() {
		request = given().baseUri(domainUrl);
		response = request.when().get(serviceUrl);
		body = response.getBody();
		System.out.println("Body: " + "\n\n" + body.prettyPrint() + "\n");
		response.then().assertThat().statusCode(200)
		.and().assertThat().body("meta.pagination.total", greaterThan(1000))
		.and().assertThat().body("meta.pagination.total", greaterThanOrEqualTo(1703))
		.and().assertThat().body("meta.pagination.total", lessThan(2000))
		.and().assertThat().body("meta.pagination.total", lessThanOrEqualTo(1704));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
