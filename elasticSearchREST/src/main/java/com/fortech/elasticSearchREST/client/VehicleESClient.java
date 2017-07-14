package com.fortech.elasticSearchREST.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.fortech.elasticSearchREST.model.Vehicle;

public class VehicleESClient {
	
	private Client client;
	
	public VehicleESClient() {
		client = ClientBuilder.newClient();
	}
	
	/////
	
	

}
