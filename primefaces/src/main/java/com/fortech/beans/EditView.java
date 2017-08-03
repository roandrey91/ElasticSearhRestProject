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

@ManagedBean(name="dtEditView")
@ViewScoped
public class EditView implements Serializable {

	private List<Vehicle> cars1;
	private List<Vehicle> cars2;

	@ManagedProperty("#{carService}")
	private CarService service;

	@PostConstruct
	public void init() {
		cars1 = service.getVehiclesFromDb();
		cars2 = service.getVehiclesFromDb();
	}

	public List<Vehicle> getCars1() {
		return cars1;
	}

	public List<Vehicle> getCars2() {
		return cars2;
	}

	public void setService(CarService service) {
		this.service = service;
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited", ((Vehicle) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((Vehicle) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

//	public void onCellEdit(CellEditEvent event) {
//		Object oldValue = event.getOldValue();
//		Object newValue = event.getNewValue();

//		if(newValue != null && !newValue.equals(oldValue)) {
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//			FacesContext.getCurrentInstance().addMessage(null, msg);
//		}
//	}
}