package com.fortech.beans;


import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.fortech.client.VehicleClient;
import com.fortech.data.Vehicle;
import com.google.gson.Gson;

/**
 * @author andreig.muresan
 */

@ManagedBean(name="vehicleDb")
@RequestScoped
public class VehicleBeanView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String brandName;
	private String bodyType;
	private String fuelType;
	private String transmission;
	private String color;
	private String location;
	
	private Gson gson = new Gson();

	private Vehicle vehicle;

	private VehicleClient vehicleClient = new VehicleClient();
	
	public void save() {
		 
		vehicle.setBodyType(bodyType);
		vehicle.setBrandName(brandName);
		vehicle.setFuelType(fuelType);
		vehicle.setColor(color);
		vehicle.setTransmission(transmission);
		vehicle.setVehicleLocation(location);
		
		vehicleClient.saveToDb( gson.toJson(vehicle, Vehicle.class));		  
		ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
//		RequestContext.getCurrentInstance().update("form:panel");
	try {
		eContext.redirect(eContext.getRequestContextPath() + "/faces/vehicleDB.xhtml");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}			
	}
	
	
	public String goToList(){
		return "vehicleDB.xhtml";
	}
		
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    
}
