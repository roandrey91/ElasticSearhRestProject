package com.fortech.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.fortech.data.Vehicle;
import com.fortech.services.CarService;
import com.google.gson.Gson;

@ManagedBean(name="dtVehicleDBView")
@ViewScoped
public class VehicleDBView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String INDEX_NAME = "vehicles";
	private final String TYPE_NAME = "vehicle";
	
	private Gson gson = new Gson();
	
	private List<Vehicle> cars;
    
    @ManagedProperty("#{carService}")
    private CarService service;
 
    @PostConstruct
    public void init() {
        cars = service.getVehiclesFromDb();
    }
    
    public void remove(Vehicle vehicle){
    	FacesMessage msg = new FacesMessage("Vehicle Removed", vehicle.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	cars.remove(vehicle);
    	service.deleteFromDb(vehicle.getId().toString());   
    }
  
    public void transferAll(){
    	FacesMessage msg = new FacesMessage("Vehicles Transfered Successfully");
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    	service.transferAllVehiclesFromDbToEs(INDEX_NAME, TYPE_NAME);
    }
    
    public void transferOne(Vehicle vehicle){
    	FacesMessage msg = new FacesMessage("Vehicle Transfered Successfully", vehicle.getId().toString() );
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    	service.transferOneVehicleFromDbToEs(INDEX_NAME, INDEX_NAME, vehicle.getId().toString());
    }
	public void onRowEdit(RowEditEvent event) {
		Vehicle vehicle = (Vehicle) event.getObject();
		FacesMessage msg = new FacesMessage("Vehicle Edited", ((Vehicle) event.getObject()).getId().toString());
		service.updateToDb(gson.toJson(vehicle, Vehicle.class));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	System.out.println(((Vehicle)event.getObject()).toString());
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((Vehicle) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
          
    public List<Vehicle> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
	
	
}
