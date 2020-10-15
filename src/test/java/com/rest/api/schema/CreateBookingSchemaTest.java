package com.rest.api.schema;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateBookingSchemaTest {
	
	RequestSpecification request;
	Response response;
	ResponseBody  body;
	String domainURL = "https://restful-booker.herokuapp.com";
	String serviceURL = "/booking";
	JsonPath jPath;
	
	@Test
	public void bookingSchemaTest() {
		request = given().baseUri(domainURL).contentType(ContentType.JSON).body(new File("src/test/java/dataFiles/booking.json"));
		response = request.when().post(serviceURL);
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body(matchesJsonSchemaInClasspath("CreateBooking_Schema.json"));	
	}

}
