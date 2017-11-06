package com.fortech.beans;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.fortech.data.VehicleES;
import com.fortech.services.CarService;

@ManagedBean(name="dtVehicleESView")
@ViewScoped
public class VehicleESView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<VehicleES> cars;

	@ManagedProperty("#{carService}")
	private CarService service;

	@PostConstruct
	public void init() {
		cars = service.getVehiclesFromES();
	}

	public String remove(VehicleES vehicleES){
//		FacesMessage msg = new FacesMessage("Vehicle Removed", vehicleES.getId().toString());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
		cars.remove(vehicleES);
		service.deleteFromES(vehicleES.get_id().toString());  
    	return "vehicleES.xhtml?faces-redirect=true";

	}

	public List<VehicleES> getCars() {
		if (cars == null){
			cars = service.getVehiclesFromES();
		}
		return cars;
	}

	public void setService(CarService service) {
		this.service = service;
	}
}