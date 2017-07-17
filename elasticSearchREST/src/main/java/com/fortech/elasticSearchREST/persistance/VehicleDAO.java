package com.fortech.elasticSearchREST.persistance;

import java.util.List;

import com.fortech.elasticSearchREST.model.Vehicle;

public interface VehicleDAO {
	
	void saveVehicle(Vehicle vehicle);

	void deleteVehicle(long id);

	void updateVehicle(Vehicle vehicle);

	Vehicle findVehicleById(long id);
	
	List<Vehicle> readAll();

}