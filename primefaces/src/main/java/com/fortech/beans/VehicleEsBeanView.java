<<<<<<< HEAD
package com.fortech.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.fortech.data.VehicleES;
import com.fortech.services.CarService;
import com.google.gson.Gson;

/**
 * @author andreig.muresan
 */

@ManagedBean(name="vehicleES")
@ViewScoped
public class VehicleEsBeanView implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String INDEX_NAME = "vehicles";
	private final String TYPE_NAME = "vehicle";


	private String brandName;
	private String bodyType;
	private String fuelType;
	private String transmission;
	private String color;
	private String location;
	private String registracionDate;
	private List<String> selectedTags;
	private List<String> tags;

	private Gson gson = new Gson();

	private VehicleES vehicle;

	@ManagedProperty("#{carService}")
	private CarService service;

	@PostConstruct
	public void init() {
		tags = new ArrayList<>();
		tags.add("new car");
		tags.add("best car");
		tags.add("automatic");
		tags.add("trust seller");
		tags.add("test");
		tags.add("black car");

	}

	public void save(){
		vehicle.setTags(selectedTags);
		vehicle.setId(generate4Digits());
		vehicle.setBodyType(bodyType);
		vehicle.setBrandName(brandName);
		vehicle.setFuelType(fuelType);
		vehicle.setColor(color);
		vehicle.setTransmission(transmission);
		vehicle.setVehicleLocation(location);
		vehicle.setRegistracionDate(registracionDate);

		service.saveToES(INDEX_NAME, TYPE_NAME, gson.toJson(vehicle, VehicleES.class));
		ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			eContext.redirect(eContext.getRequestContextPath() + "/faces/vehicleES.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
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

	public void setService(CarService service) {
		this.service = service;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public VehicleES getVehicle() {
		if (vehicle == null) {
			vehicle = new VehicleES();
		}
		return vehicle;
	}

	public void setVehicle(VehicleES vehicle) {
		this.vehicle = vehicle;
	}

	public String getRegistracionDate() {
		return registracionDate;
	}

	public void setRegistracionDate(String registracionDate) {
		this.registracionDate = registracionDate;
	}

	public Long generate4Digits(){
		return (long) ((Math.random()*9000)+1000);
	}

	public List<String> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(List<String> selectedTags) {
		this.selectedTags = selectedTags;
	}





}
=======
package com.fortech.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author andreig.muresan
 */

@ManagedBean(name="vehicleES")
@RequestScoped
public class VehicleEsBeanView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
}
>>>>>>> b9aa13bfbfeaeaeeb7262f65775760c048bdfc72
