package com.fortech.client;

import java.util.ArrayList;
import java.util.List;

import com.fortech.data.Vehicle;
import com.fortech.data.VehicleES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class VehicleClient {

	private Gson gson = new Gson();

	public List<VehicleES> getVehicleFromES(){

		List<VehicleES> vehicleList = new ArrayList<VehicleES>();
		
		Client client = Client.create();

		String url = "http://localhost:8080/elasticSearchREST/elasticSearch/vehicles/vehicle";

		WebResource service = client.resource(url);

		ClientResponse response = service.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output2 = response.getEntity(String.class);
		vehicleList = gson.fromJson(output2, new TypeToken<List<VehicleES>>(){}.getType());
		

		return vehicleList;
	}


	public List<Vehicle> getVehicleFromDB(){

		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		
		Client client = Client.create();

		String url = "http://localhost:8080/elasticSearchREST/vehicle/getAll";

		WebResource service = client.resource(url);

		ClientResponse response = service.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output2 = response.getEntity(String.class);
		vehicleList = gson.fromJson(output2, new TypeToken<List<Vehicle>>(){}.getType());
		

		return vehicleList;
	}

	public void saveToDb(String data){
		
		Client client = Client.create();
		final String url = "http://localhost:8080/elasticSearchREST/vehicle/add";
		WebResource webResource = client.resource(url);
		
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	
	public static void main(String[] args){
		
		Gson gson = new Gson();
		Client client = Client.create();
		final String url = "http://localhost:8080/vehicle/add";
		WebResource webResource = client.resource(url);
		Vehicle vehicle =new Vehicle();
		vehicle.setBodyType("test");
		vehicle.setBrandName("test");
		vehicle.setColor("test");
		vehicle.setFuelType("test");
		vehicle.setRegistracionDate("test");
		vehicle.setTransmission("test");
		vehicle.setVehicleLocation("test");
		vehicle.setId((long) 122);
		vehicle.setPrice((double)22);
		
		String data =gson.toJson(vehicle, Vehicle.class);
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		
		
		
	}
	
	
}
