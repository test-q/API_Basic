package com.rest.api.authentications;


import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BasicAuth_Test {
	RequestSpecification request;
	Response response;
	ResponseBody body;
	ValidatableResponse validResponse;
	ExtractableResponse<Response> extractResponse;
	String domainUrl = "https://the-internet.herokuapp.com";
	String serviceUrl = "/basic_auth";
	String bodyDataAsString;
	
	// Preemptive Basic Authentication - Credentials pass to the server before it ask.
	// That means by default we are sending username and password without server asking.
	@Test
	public void basic_Preemptive_Auth_API_Test() {

		request = given().baseUri(domainUrl).auth().preemptive().basic("admin", "admin");           // To pass basic authentication
		response = request.when().get(serviceUrl);
		
		//Once you get Response you have to find out 
		//Statuscode, Body, Header
		
		System.out.println("API Response Status Code : " + response.getStatusCode() + "\n");            // Get Status code
		response.then().assertThat().statusCode(200);                                                   // Validate the status code
		
		System.out.println("API Response Header: " + response.getHeaders() + "/n");                     // Get Header
		response.then().assertThat().header("Content-Type", equalTo("text/html;charset=utf-8"));        // Validate Header value
		
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("Response Body: "+ bodyDataAsString + "/n");                                 // Get Body
		Assert.assertEquals(bodyDataAsString.contains("The Internet"), true);	                        // Assert with testng
		
		boolean flag = bodyDataAsString.contains("The Internet");                                       // Validation without testng
		if(flag==true) {
			System.out.println("Body Contains: " + "The Internet");                                     
		}else {
			System.out.println("Something Went Wrong");
		}				
	}
	
	
	
	//Challenged Basic Authentication  
	//In the first go we are not sending any username and password we are just hitting to the server directly
	//Server respond - no i am not giving any details.. give me a username and password first
	//Second time we provide username and password then server will check/authenticate if it is valid than only it send response.
	//That means it is two way communication hence it is call Challenged Basic Authentication 
	//Minimum two request-response pair required to process one call
	@Test
	public void basic_chanllenged_Aut_API_test() {
		request = given().baseUri(domainUrl).auth().basic("admin", "admin");
		response = request.when().get(serviceUrl);
		
		System.out.println("API Response Status Code : " + response.getStatusCode() + "\n");            // Get Status code
		response.then().assertThat().statusCode(200);                                                   // Validate the status code
		
		System.out.println("API Response Header: " + response.getHeaders() + "/n");                     // Get Header
		response.then().assertThat().header("Content-Type", equalTo("text/html;charset=utf-8"));        // Validate Header value
		
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("Response Body: "+ bodyDataAsString + "/n");                                 // Get Body
		Assert.assertEquals(bodyDataAsString.contains("The Internet"), true);	                        // Assert with testng
		
		boolean flag = bodyDataAsString.contains("The Internet");                                       // Validation without testng
		if(flag==true) {
			System.out.println("Body Contains: " + "The Internet");                                     
		}else {
			System.out.println("Something Went Wrong");
		}				
	}
	
	//Diestive Authentication is more secure than Basic Authentication because it involve a new digestive key
	//In the first go we are not sending any username and password we are just hitting to the server directly
	//Server respond - no i am not giving any details.. give me a username and password first
	//Second time we provide username and password with digestive key then server will check if it is valid than only it send response.
	//It is two way communication Also need username, password with digestive key hence it is more secure 
	//Minimum two request-response pair required to process one call
	
	@Test
	public void basic_digestive_Aut_API_test() {
		request = given().baseUri(domainUrl).auth().digest("admin", "admin");
        response = request.when().get(serviceUrl);
		
		System.out.println("API Response Status Code : " + response.getStatusCode() + "\n");            // Get Status code
		response.then().assertThat().statusCode(200);                                                   // Validate the status code
		
		System.out.println("API Response Header: " + response.getHeaders() + "/n");                     // Get Header
		response.then().assertThat().header("Content-Type", equalTo("text/html;charset=utf-8"));        // Validate Header value
		
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("Response Body: "+ bodyDataAsString + "/n");                                 // Get Body
		Assert.assertEquals(bodyDataAsString.contains("The Internet"), true);	                        // Assert with testng
		
		boolean flag = bodyDataAsString.contains("The Internet");                                       // Validation without testng
		if(flag==true) {
			System.out.println("Body Contains: " + "The Internet");                                     
		}else {
			System.out.println("Something Went Wrong");
		}		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
