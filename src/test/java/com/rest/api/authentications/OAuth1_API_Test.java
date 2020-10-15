package com.rest.api.authentications;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class OAuth1_API_Test {

	RequestSpecification request;
	Response response;
	ResponseBody body;
	ValidatableResponse validResponse;
	String domainUrl = "NEED TWITTER API";
	String serviceUrl = "NEED TWITTER API";
	String bodyDataAsString;

	
	// If you don't have OAuth1.0 Token Than how to generate OAuth2.0 token
	// Step1 : Generate token at run time
	// Step2 : Get String TokenId from response
	// Step3 : Hit API with this token ID
	@Test(enabled = false)
	public void TwitterAPI_OAuth1_Test() {
		// Generate token - Dummy test bcoz we dont have twitter api
		request = given().auth().oauth("consumerKeyValue", "consumerSecretValue", "accessTokenValue",
				"secretTokenValue");
		// Now hit the api
		response = request.when().post("EnterAPIUrl");
	}

}
