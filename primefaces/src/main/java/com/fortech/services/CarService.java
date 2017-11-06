package com.fortech.services;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.fortech.client.VehicleClient;
import com.fortech.data.Vehicle;
import com.fortech.data.VehicleES;


@ManagedBean(name = "carService")
@ApplicationScoped
public class CarService implements Serializable {

	private static final long serialVersionUID = -8325317581159305904L;
	
	private VehicleClient client = new VehicleClient();

	public List<VehicleES> getVehiclesFromES(){
		return client.getVehicleFromES();
	}

	public List<Vehicle> getVehiclesFromDb(){
		return client.getVehicleFromDB();
	}

	public void deleteFromDb(String vehicleId){
		client.deleteVehicleFromDb(vehicleId);
	}

	public void updateToDb(String data){
		client.updateVehicleToDb(data);
	}
	
	public void transferAllVehiclesFromDbToEs(String index, String type){
		client.transferAllVehiclesFromDbToEs(index, type);
	}
	
	public void transferOneVehicleFromDbToEs(String index, String type, String id){
		client.transferVehicleFromDbToEs(index, type, id);
	}
	
	public void saveToES(String index, String type, String data){
		client.saveVehicleToES(index, type, data);
	}

	public void deleteFromES(String vehicleId) {
		client.deleteVehicleFromES(vehicleId);	
	}
	
	public List<VehicleES> searchVehicleEs(String field, String value){
		return client.searchByFieldFromEs(field, value);
	}
	
	public Long countNumberFromSearchEs(String field, String value){
		return client.countNumberOfSearching(field, value);
	}

}