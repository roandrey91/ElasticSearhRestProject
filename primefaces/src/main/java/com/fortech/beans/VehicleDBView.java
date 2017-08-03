package com.fortech.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fortech.data.Vehicle;
import com.fortech.services.CarService;

@ManagedBean(name="dtVehicleDBView")
@ViewScoped
public class VehicleDBView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Vehicle> cars;
    
    @ManagedProperty("#{carService}")
    private CarService service;
 
    @PostConstruct
    public void init() {
        cars = service.getVehiclesFromDb();
    }
    
    public List<Vehicle> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
	
	
}
