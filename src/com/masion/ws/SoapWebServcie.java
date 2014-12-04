package com.masion.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;

@WebService
public class SoapWebServcie {

	@WebMethod
	public String hello(String name) {
		return "Hello, " + name + "/n";
	}

	public static void main(String[] args) {
		// create and publish an endpoint
		SoapWebServcie hello = new SoapWebServcie();
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/hello", hello);
	}
}
