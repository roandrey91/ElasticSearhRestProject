package com.fortech.beans;


import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.fortech.client.VehicleClient;
import com.fortech.data.Vehicle;
import com.google.gson.Gson;

/**
 * @author andreig.muresan
 */

@ManagedBean(name="vehicleDb")
@SessionScoped
public class VehicleBeanView implements Serializable{

	private static final long serialVersionUID = 1L;

	private Gson gson = new Gson();

	private Vehicle vehicle;

	private VehicleClient vehicleClient = new VehicleClient();


	public void save() {
		vehicleClient.saveToDb( gson.toJson(vehicle, Vehicle.class));
		ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
	try {
		eContext.redirect(eContext.getRequestContextPath() + "/faces/vehicleDB.xhtml");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}			
	}


	public Vehicle getVehicle() {
		if(vehicle == null){
			vehicle = new Vehicle();
		}
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}



}
