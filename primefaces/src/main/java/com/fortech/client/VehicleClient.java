<<<<<<< HEAD
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

	private final String INDEX_NAME = "vehicles";
	private final String TYPE_NAME = "vehicle";

	private final String URL_GET_VEHICLES_FROM_ES = "http://localhost:8080/elasticSearchREST/elasticSearch/vehicles/vehicle";
	private final String URL_GET_VEHICLES_FROM_DB = "http://localhost:8080/elasticSearchREST/vehicle/getAll";
	private final String URL_SAVE_VEHICLE_TO_DB = "http://localhost:8080/elasticSearchREST/vehicle/add";
	private final String URL_DELETE_VEHICLE_FROM_DB = "http://localhost:8080/elasticSearchREST/vehicle/delete";
	private final String URL_UPDATE_VEHICLE_TO_DB = "http://localhost:8080/elasticSearchREST/vehicle/update";
	private final String URL_TRANSFER_VEHICLE_FROM_DB_TO_ES = "http://localhost:8080/elasticSearchREST/dbes/transfer";
	private final String URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES = "http://localhost:8080/elasticSearchREST/dbes/transferall";
	private final String URL_SAVE_VEHICLE_TO_ES = "http://localhost:8080/elasticSearchREST/elasticSearch/add";
	private String URL_UPDATE_VEHICLE_TO_ES ;
	private  String URL_DELETE_ALL_VEHICLE_FROM_ES;
	private final String URL_DELETE_ONE_VEHICLE_FROM_ES = "http://localhost:8080/elasticSearchREST/elasticSearch/delete";
	private final String URL_SEARH_BY_FIELD_IN_ES = "http://localhost:8080/elasticSearchREST/elasticSearch/getall";

	public List<VehicleES> getVehicleFromES(){
		List<VehicleES> vehicleList = new ArrayList<VehicleES>();

		Client client = Client.create();

		WebResource service = client.resource(URL_GET_VEHICLES_FROM_ES);

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

		WebResource service = client.resource(URL_GET_VEHICLES_FROM_DB);

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

		WebResource webResource = client.resource(URL_SAVE_VEHICLE_TO_DB);

		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void deleteVehicleFromDb(String vehicleId) {
		Client client = Client.create();

		WebResource webResource = client.resource(URL_DELETE_VEHICLE_FROM_DB);

		ClientResponse response = webResource.path(vehicleId).delete(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Deleted");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void updateVehicleToDb(String data){
		Client client = Client.create();

		WebResource webResource = client.resource(URL_UPDATE_VEHICLE_TO_DB);
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Update go to server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void transferAllVehiclesFromDbToEs(String index, String type){
		Client client = Client.create();

		WebResource webResource = client.resource(URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES);
		ClientResponse response = webResource.path(index).path(type).accept("application/json").type("application/json").post(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Transfer succeed");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void transferVehicleFromDbToEs(String index, String type, String id){
		Client client = Client.create();

		WebResource webResource = client.resource(URL_TRANSFER_VEHICLE_FROM_DB_TO_ES);
		ClientResponse response = webResource.path(index).path(type).path(id).accept("application/json").type("application/json").post(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Transfer succeed");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void saveVehicleToES(String index, String type, String data){
		Client client = Client.create();

		WebResource webResource = client.resource(URL_SAVE_VEHICLE_TO_ES);
		ClientResponse response = webResource.path(index).path(type).accept("application/json").type("application/json").post(ClientResponse.class, data);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Transfer succeed");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void deleteVehicleFromES(String vehicleId) {
		Client client = Client.create();

		WebResource webResource = client.resource(URL_DELETE_ONE_VEHICLE_FROM_ES);

		ClientResponse response = webResource.path(INDEX_NAME).path(TYPE_NAME).path(vehicleId).delete(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Deleted");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public List<VehicleES> searchByFieldFromEs (String field, String value){
		List<VehicleES> vehicleList = new ArrayList<>();		

		Client client = Client.create();

		WebResource webResource = client.resource(URL_SEARH_BY_FIELD_IN_ES);

		ClientResponse response = webResource.path(INDEX_NAME).path(TYPE_NAME).path(field).path(value).accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output2 = response.getEntity(String.class);
		vehicleList = gson.fromJson(output2, new TypeToken<List<VehicleES>>(){}.getType());

		return vehicleList;
	}


	//	public static void main(String[] args){
	//	
	//		transferAllVehiclesFromDbToEs("vehicles", "vehicle");
	//		
	//		/{index}/{type}/{id}
	//		
	//	}



}
=======
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
	
	private String URL_GET_VEHICLES_FROM_ES = "http://localhost:8080/elasticSearchREST/elasticSearch/vehicles/vehicle";
	private String URL_GET_VEHICLES_FROM_DB = "http://localhost:8080/elasticSearchREST/vehicle/getAll";
	private String URL_SAVE_VEHICLE_TO_DB = "http://localhost:8080/elasticSearchREST/vehicle/add";
	private String URL_DELETE_VEHICLE_FROM_DB = "http://localhost:8080/elasticSearchREST/vehicle/delete";
	private String URL_UPDATE_VEHICLE_TO_DB = "http://localhost:8080/elasticSearchREST/vehicle/update";
	private String URL_TRANSFER_VEHICLE_FROM_DB_TO_ES = "http://localhost:8080/elasticSearchREST/dbes/transfer";
	private String URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES = "http://localhost:8080/elasticSearchREST/dbes/transferall";

	public List<VehicleES> getVehicleFromES(){

		List<VehicleES> vehicleList = new ArrayList<VehicleES>();
		
		Client client = Client.create();
	
		WebResource service = client.resource(URL_GET_VEHICLES_FROM_ES);

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

		WebResource service = client.resource(URL_GET_VEHICLES_FROM_DB);

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
	
		WebResource webResource = client.resource(URL_SAVE_VEHICLE_TO_DB);
		
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public void deleteVehicleFromDb(String vehicleId) {
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(URL_DELETE_VEHICLE_FROM_DB);
		
		ClientResponse response = webResource.path(vehicleId).delete(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}

		System.out.println("Deleted");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public void updateVehicleToDb(String data){
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(URL_UPDATE_VEHICLE_TO_DB);
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Update go to server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public void transferAllVehiclesFromDbToEs(String index, String type){
		
		Client client = Client.create();

		WebResource webResource = client.resource(URL_TRANSFER_ALL_VEHICLES_FROM_DB_TO_ES);
		ClientResponse response = webResource.path(index).path(type).accept("application/json").type("application/json").post(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Transfer succeed");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public void transferVehicleFromDbToEs(String index, String type, String id){
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(URL_TRANSFER_VEHICLE_FROM_DB_TO_ES);
		ClientResponse response = webResource.path(index).path(type).path(id).accept("application/json").type("application/json").post(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
		
		System.out.println("Transfer succeed");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	
//	public static void main(String[] args){
//	
//		transferAllVehiclesFromDbToEs("vehicles", "vehicle");
//		
//		
//		
//	}
	
	
	
}
>>>>>>> b9aa13bfbfeaeaeeb7262f65775760c048bdfc72
