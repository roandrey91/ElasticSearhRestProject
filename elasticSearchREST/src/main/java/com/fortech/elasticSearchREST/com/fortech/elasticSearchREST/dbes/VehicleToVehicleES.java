package com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;

public class VehicleToVehicleES {

	public static VehicleES createNewVehicleESFromVehicle(Vehicle vehicle, String generateId, String[] tags) {
		VehicleES vehicleES = new VehicleES();
		vehicleES.setElasticSearchId(generateId);
		vehicleES.setId(vehicle.getId());
		vehicleES.setBrandName(vehicle.getBrandName());
		vehicleES.setBodyType(vehicle.getBodyType());
		vehicleES.setFuelType(vehicle.getFuelType());
		vehicleES.setRegistracionDate(vehicle.getRegistracionDate());
		vehicleES.setColor(vehicle.getColor());
		vehicleES.setTransmission(vehicle.getTransmission());
		vehicleES.setPrice(vehicle.getPrice());
		vehicleES.setVehicleLocation(vehicle.getVehicleLocation());
		vehicleES.setTags(tags);
		return vehicleES;
	}

	public static List<VehicleES> populateList (List<Vehicle> vehicleList, String[] tags){
		List<VehicleES> vehicleESlist = new ArrayList<>();
		
		for (Vehicle vehicle : vehicleList){
			String createdId = createId(vehicle.getId().toString(), vehicle.getRegistracionDate());
			VehicleES vehicleES = createNewVehicleESFromVehicle(vehicle, createdId, tags);
			vehicleESlist.add(vehicleES);
			}
		
		return vehicleESlist;
	}
	
	public static String createId(String referenceId, String registracionDate){
		String id = String.format("%s%s", referenceId, registracionDate);
		String result = DigestUtils.md5Hex(id);
		return result;
	}
}
