package com.fortech.services;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.fortech.client.VehicleClient;
import com.fortech.data.Vehicle;
import com.fortech.data.VehicleES;


@ManagedBean(name = "carService")
@ApplicationScoped
public class CarService {

	
	private VehicleClient client = new VehicleClient();

	public List<VehicleES> getVehiclesFromES(){
		return client.getVehicleFromES();
	}

	public List<Vehicle> getVehiclesFromDb(){
		return client.getVehicleFromDB();
	}

	public void saveToDb(){
		
	}

}