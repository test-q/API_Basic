package com.rest.api.authentications;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class OAuth2_API_Test {
	
	RequestSpecification request;
	Response response;
	ResponseBody body;
	ValidatableResponse validResponse;
	String domainUrl = "http://coop.apps.symfonycasts.com";
	String serviceUrl = "/api/1434/chickens-feed";
	String bodyDataAsString;
	String accessTokenURL = "http://coop.apps.symfonycasts.com/token";
	JsonPath jpath;
	
	//If you already have OAuth2.0 Token and use it WithouHeader
	//That means select authorization tab and select OAuth2.0 Type in drop down box
	//That means select authorization tab and select BearerToken Type in drop down box
	@Test
	public void OAuth2_WithoutHeader_Test() {
		request = given().baseUri(domainUrl).auth().oauth2("ec8d4f281973a5f01395d785ef228a09678c6257");	
		response = request.when().post(serviceUrl);
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("API Response Status Code: " + response.getStatusCode() + "\n");
		response.then().assertThat().statusCode(200);
		
		System.out.println("API Response Header: " + response.getHeaders() + "\n");
		response.then().assertThat().header("Content-Type", equalTo("application/json"));
		
		System.out.println("API Response Body: " + body.prettyPrint() + "\n");    //If you body in json format 
		response.then().assertThat().body("success", equalTo(true));
		
		jpath = response.jsonPath();
		System.out.println("Check API Body With JSONPath: " + jpath.getString("message"));
	}

	//If you already have OAuth2.0 Token and use it WithHeader
	//That means select header tab and pass key and value
	@Test
	public void OAuth2_WithHeader_Test() {
		request = given().baseUri(domainUrl).headers("Authorization", "Bearer f056048d12b06413988537f763124d9ea47200cf");
		response = request.when().post(serviceUrl);
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("API Response Status Code: " + response.getStatusCode() + "\n");
		response.then().assertThat().statusCode(200);
		
		System.out.println("API Response Header: " +response.getHeaders() + "\n");
		response.then().assertThat().header("Content-Type", equalTo("application/json"));
		
		System.out.println("API Response Body: " + body.prettyPrint() + "\n");
		response.then().assertThat().body("success", equalTo(true));	
		
	}
	
	//If you already have OAuth2.0 Token and use it WithHeader
	//That means select header tab and pass key and value
	//Also Pass 2 query parameter
	@Test
	public void OAuth2_WithHeader_With2QueryParam_Test() {
		String domainUrl1 = "https://gorest.co.in";
		String serviceUrl1 = "/public-api/users";
		request = given().baseUri(domainUrl1).header("Authorization", "Bearer 0ea226be7a379cc3f7951c9841679e1b584bcb4e3601914b1626b444726ee655")
					.queryParam("name", "Abani Talwar").queryParam("email", "abani_talwar@hagenes-hansen.co");
		response = request.when().get(serviceUrl1);
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("API Response Status Code: " + response.getStatusCode() + "\n");
		response.then().assertThat().statusCode(200);
		
		System.out.println("API Response Header: " + "\n\n" + response.getHeaders() + "\n");
		response.then().assertThat().header("Server", equalTo("nginx"));
		
		System.out.println("API Response Body: " +"\n\n" +  body.prettyPrint() + "\n");
		response.then().assertThat().body("data.name[0]", equalTo("Abani Talwar"));
		response.then().assertThat().body("meta.pagination.total", comparesEqualTo(1));		
		
	}
	
	//If you don't have OAuth2.0 Token Than how to generate OAuth2.0 token
		//Step1 : Generate token at run time
		//Step2 : Get String TokenId from response
		//Step3 : Hit API with this token ID
	@Test
	public void get_OAuth_Token_Test() {
		// Generate token 
		request = given().formParam("client_id", "RupaliTestApp")
						 .formParam("client_secret", "b8cd77bc5db0c3bde9b1b2ec3c08fc84")
						 .formParam("grant_type", "client_credentials");
		
		response = request.when().post(accessTokenURL);
		body = response.getBody();
		bodyDataAsString = body.asString();
		
		System.out.println("Status Code: " + response.getStatusCode() + "\n");
		response.then().assertThat().statusCode(200);
		
		System.out.println("OutPut: " + "\n\n" +  response.prettyPrint() + "\n");
		
		String token_id = body.jsonPath().getString("access_token");
		System.out.println("API Token Id : " + token_id);
		
		//Now hit the api
		RequestSpecification request1 = given().baseUri(domainUrl).auth().oauth2(token_id);
		Response response1 = request1.when().post(serviceUrl);
		ResponseBody body1 = response1.getBody();
		jpath = response1.jsonPath();
		
		System.out.println("API Status Code: " + response1.getStatusCode() + "\n");
		response1.then().assertThat().statusCode(200);
		
		System.out.println("API Response Body: " + "\n\n" + body1.prettyPrint() + "\n");
		System.out.println("Message with jsonpath: " + jpath.getString("message"));
		response1.then().assertThat().body("success", comparesEqualTo(true));
		
		System.out.println("API Response Header: " + "\n\n" +  response1.getHeaders() + "\n");
		response1.then().assertThat().header("Content-Type", "application/json");			
	}
	

	
	

}
