package com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes;


import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;

import java.util.ArrayList;
import java.util.List;

public class VehicleToVehicleES {

    public static VehicleES createNewVehicleESFromVehicle(Vehicle vehicle, String[] tags) {
        VehicleES vehicleES = new VehicleES();
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
                VehicleES vehicleES = createNewVehicleESFromVehicle(vehicle, tags);
                vehicleESlist.add(vehicleES);
        }
        return vehicleESlist;
    }
}