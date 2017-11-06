package com.fortech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fortech.client.VehicleClient;
import com.fortech.services.CarService;

@ManagedBean
@ViewScoped
public class TestBean implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private String value;
	
	private CarService carService = new CarService();
	private VehicleClient vehicleClient = new VehicleClient();
	
	List<String> results = new ArrayList<>();

//	public List<String> complete(String querry){
//		List<String> allWords = vehicleClient.
//	}
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
