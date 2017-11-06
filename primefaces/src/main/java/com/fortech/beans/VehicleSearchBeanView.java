package com.fortech.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.fortech.data.VehicleES;
import com.fortech.services.AutoCompleService;
import com.fortech.services.CarService;


@ManagedBean(name="searchEs")
@ViewScoped
public class VehicleSearchBeanView implements Serializable {

	private static final long serialVersionUID = 7981531046676031568L;

	private String field;
	private String value;
	private String result;
	private String text;

	private List<VehicleES> cars;
	private List<String> autoCompleteTextlist;

	
	@ManagedProperty("#{carService}")
	private CarService service;

	@ManagedProperty("#{autocomplete}")
	private AutoCompleService service1;

	@PostConstruct
	public void init(){
		field = "brandName";
		autoCompleteTextlist = new ArrayList<>(service1.getFieldValue(field));
	}

	public void countSearch(String field, String text){
       System.out.println(field + "   " + text);
		result = service.countNumberFromSearchEs(field, text).toString();
		value = text;
		System.out.println(value);
		System.out.println(result);
	}

	public void searchVehicles(String field, String text){
		cars = service.searchVehicleEs(field, text);
		System.out.println(text);
		for(VehicleES vehicleES : cars){
			System.out.println(vehicleES.getId());
		}
		System.out.println(cars);
	}


	public void load() {
		System.out.println(getField());
		autoCompleteTextlist.clear(); 
		autoCompleteTextlist = service1.getFieldValue(getField());
	}
	
	public List<String> completeText(String querry){
		List<String> allWords = getAutoCompleteTextlist();
		List<String> filteredWords = new ArrayList<>();
		
		for(int i = 0; i < allWords.size(); i++){
			text = allWords.get(i);
			if (text.toLowerCase().startsWith(querry)) {
				filteredWords.add(text);
			}
		}
		return filteredWords;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setService(CarService service) {
		this.service = service;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setService1(AutoCompleService service1) {
		this.service1 = service1;
	}

	public List<VehicleES> getCars() {
		return cars;
	}

	public void setCars(List<VehicleES> cars) {
		this.cars = cars;
	}
	
	public List<String> getAutoCompleteTextlist() {
		return autoCompleteTextlist;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static void main(String[] args) {
//		VehicleSearchBeanView vehicleSearchBeanView = new VehicleSearchBeanView();
//		AutoCompleService autoCompleService = new AutoCompleService();
//		CarService carService = new CarService();
//		AutoComplete autoComplete = new AutoComplete(1, "Ford", "ford");
//		vehicleSearchBeanView.searchVehicles("brandName", autoComplete);
//		vehicleSearchBeanView.countSearch("brandName", 
//				autoComplete);
//		System.out.println(carService.countNumberFromSearchEs("brandName", "ford"));

	}

}
