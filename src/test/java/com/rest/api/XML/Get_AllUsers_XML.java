package com.rest.api.XML;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class Get_AllUsers_XML {
	
	RequestSpecification request;
	Response response;
	String domainUrl = "https://gorest.co.in";
	String serviceUrl = "/public-api/users.xml";
	XmlPath xmlPath;
	
	@Test
	public void getAllUser_Xml_Test() {
		request = given().baseUri(domainUrl);
		response = request.when().log().all().get(serviceUrl);
		
		xmlPath = response.xmlPath();
		String total = xmlPath.get("hash.meta.pagination.total");
		System.out.println("Total Pages: " + total);
		
		response.then().assertThat().statusCode(200);
	}
	
	
	

}
