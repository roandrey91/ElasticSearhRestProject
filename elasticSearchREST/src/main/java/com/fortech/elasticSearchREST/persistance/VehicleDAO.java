package com.fortech.elasticSearchREST.persistance;

import java.util.List;

import com.fortech.elasticSearchREST.model.Vehicle;

public interface VehicleDAO {
	
	void saveVehicle(long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, double price, String vehicleLocation);

	void deleteVehicle(long id);

	void updateVehicle(long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, double price, String vehicleLocation);

	Vehicle findVehicleById(long id);
	
	List<Vehicle> readAll();

}