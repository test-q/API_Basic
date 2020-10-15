package com.rest.api.authentications;

import org.testng.annotations.Test;

import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FormAuth_Test {
	RequestSpecification request;
	Response response;
	ResponseBody body;
	ValidatableResponse validResponse;
	ExtractableResponse<Response> extractResponse;
	String domainUrl = "";
	String serviceUrl = "/basic_auth";
	String bodyDataAsString;
	
	@Test
	public void basic_Form_Auth_Test() {
		request = given().log().all().auth()
		.form("batchautomation", "Test@12345", new FormAuthConfig("https://classic.freecrm.com/system/authenticate.cfm", "username", "password"));
		
		response = request.when().log().all().post("https://classic.freecrm.com/system/authenticate.cfm");
		
		response.then().assertThat().statusCode(200);
		
	}

}
