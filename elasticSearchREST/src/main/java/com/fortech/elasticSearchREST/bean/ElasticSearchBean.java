package com.fortech.elasticSearchREST.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.ManagedBean;

import com.fortech.elasticSearchREST.model.VehicleES;

@ManagedBean
public class ElasticSearchBean {

	private String tag;
	private VehicleES selectVehicle;
	private VehicleES vehicleES = new VehicleES();
	private List<VehicleES> vehicleList = new ArrayList<>();
	
	private String wildCardQuery;
	//Getters & Setters
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public VehicleES getSelectVehicle() {
		return selectVehicle;
	}
	
	public void setSelectVehicle(VehicleES selectVehicle) {
		this.selectVehicle = selectVehicle;
	}
	
	public VehicleES getVehicleES() {
		return vehicleES;
	}
	
	public void setVehicleES(VehicleES vehicleES) {
		this.vehicleES = vehicleES;
	}
	
	public List<VehicleES> getVehicleList() {
		return vehicleList;
	}
	
	public void setVehicleList(List<VehicleES> vehicleList) {
		this.vehicleList = vehicleList;
	}
	
	public String getWildCardQuery() {
		return wildCardQuery;
	}

	public void setWildCardQuery(String wildCardQuery) {
		this.wildCardQuery = wildCardQuery;
	}
	
	// Methods
	

	public void vehicleSelect() {
		
		vehicleES = selectVehicle;
		String[] documentTags = selectVehicle.getTags();
		tag = "";
		
		for(int i = 0; i<documentTags.length; i++){
			documentTags[i] = documentTags[i].replace("[", "");
			documentTags[i] = documentTags[i].replace("]", "");
			tag += documentTags[i] + ",";
		}
		
		tag = tag.substring(0, tag.length()-1);
	}
	
//	public void collectionSort() {
//		
//		Collections.sort(vehicleList, new Comparator<VehicleES>() {
//
//			@Override
//			public int compare(VehicleES o1, VehicleES o2) {
//			   return o2.getPrice().compareTo(o1.getPrice());
//
//			}
//		});
//	}
//	
//	
	
	
	
}
