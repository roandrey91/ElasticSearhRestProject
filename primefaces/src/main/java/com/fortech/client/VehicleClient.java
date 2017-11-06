package com.fortech.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fortech.data.PathZipParam;
import com.fortech.data.UpFile;
import com.fortech.data.Vehicle;
import com.fortech.data.VehicleES;
import com.fortect.endPointReader.EndPointReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class VehicleClient {

	private EndPointReader endPointReader = new EndPointReader();
	private Gson gson = new Gson();

	private final String INDEX_NAME = "vehicles";
	private final String TYPE_NAME = "vehicle";
	private String[] endPoint = endPointReader.getEndPoints();
		
	public List<VehicleES> getVehicleFromES(){
		List<VehicleES> vehicleList = new ArrayList<VehicleES>();
		String url = endPoint[0];
		
		Client client = Client.create();
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
		String url = endPoint[1];
		
		Client client = Client.create();
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
		String url = endPoint[2];
		
		Client client = Client.create();
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

	public void deleteVehicleFromDb(String vehicleId) {
		String url = endPoint[3];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
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
		String url = endPoint[4];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
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
		String url = endPoint[6];
		System.out.println(url);
		Client client = Client.create();
		WebResource webResource = client.resource(url);
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
		String url = endPoint[5];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
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
		String url = endPoint[7];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
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
		String url = endPoint[8];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.path(INDEX_NAME).path(TYPE_NAME).path(vehicleId).delete(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Deleted");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public List<VehicleES> searchByFieldFromEs(String field, String value){
		List<VehicleES> vehicleList = new ArrayList<>();	
		String url = endPoint[9];

		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.path(INDEX_NAME).path(TYPE_NAME).path(field).path(value).accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output2 = response.getEntity(String.class);
		vehicleList = gson.fromJson(output2, new TypeToken<List<VehicleES>>(){}.getType());

		return vehicleList;
	}

	public Long countNumberOfSearching(String field, String value){
		String url = endPoint[10];
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.path(INDEX_NAME).path(TYPE_NAME).path(field).path(value).accept("application/json").get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		String output2 = response.getEntity(String.class);
		Long countNumber = Long.parseLong(output2);
		
		return countNumber;
	}
	
	public List<String> getFieldValueFromEs(String field){
		String url = endPoint[11];
		
		Client client = Client.create();
		WebResource service = client.resource(url);
		ClientResponse response = service.path(INDEX_NAME).path(TYPE_NAME).path(field).accept("application/json").get(ClientResponse.class);
		
		String output =response.getEntity(String.class);
			
		List<String> valueList = new ArrayList<>(Arrays.asList(output.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").split(",")));

		return valueList;
	}
	
	public UpFile getFileZipContent(String path){
		String url = endPoint[12];
		PathZipParam param = new PathZipParam(path);
		String data = gson.toJson(param);
		
		Client client = Client.create();
		WebResource service = client.resource(url);
        ClientResponse response = service.accept("application/json").type("application/json").post(ClientResponse.class, data);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		String output = response.getEntity(String.class);
		UpFile upFile = gson.fromJson(output, UpFile.class);
		
		return upFile;
	}
	
	public static void main(String[] args){
		VehicleClient vehicleClient = new VehicleClient();
		
		System.out.println(vehicleClient.getFileZipContent("C:\\Users\\andreig.muresan\\Downloads\\test2.zip"));
	}
}
