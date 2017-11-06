package com.fortech.services;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.fortech.client.VehicleClient;

@ManagedBean(name="autocomplete", eager = true)
@ApplicationScoped
public class AutoCompleService {
	
	private VehicleClient client = new VehicleClient();
	
	public List<String> getFieldValue(String field){		
		List<String> valueList= client.getFieldValueFromEs(field);
		return valueList;
	}
		
}
