package com.fortech.elasticSearchREST.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.fortech.elasticSearchREST.model.Vehicle;

public class VehicleClient {

	private Client client;

	public VehicleClient() {
		client = ClientBuilder.newClient();
	}


	public List<Vehicle> get() {
		WebTarget target = client.target("http://localhost:8080/elasticSearchREST/webapi/");

		List<Vehicle> response = target.path("vehicle/getAll").request("application/json").get(new GenericType<List<Vehicle>>() {});

		return response;
	} 

	public Vehicle get(long id) {
		WebTarget target = client.target("http://localhost:8080/elasticSearchREST/webapi/");

		Response response = target.path("vehicle/" + id).request("application/json").get(Response.class);

		if(response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": there was an error on the server.");
		}

		return response.readEntity(Vehicle.class);
	}

	public Vehicle create(Vehicle vehicle) {
		WebTarget target = client.target("http://localhost:8080/elasticSearchREST/webapi/");

		Response response = target.path("vehicle/add")
				.request("application/json")
				.post(Entity.entity(vehicle, "application/json"));

		if(response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": there was an error on the server.");
		}

		return response.readEntity(Vehicle.class);
	}

	public Vehicle update(Vehicle vehicle) {
		WebTarget target = client.target("http://localhost:8080/elasticSearchREST/webapi/");

		Response response = target.path("vehicle/update")
				.request("application/json")
				.post(Entity.entity(vehicle, "application/json"));

		if(response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": there was an error on the server.");
		}

		return response.readEntity(Vehicle.class);
	}
}
