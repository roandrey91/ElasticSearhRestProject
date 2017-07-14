package com.fortech.elasticSearchREST.services;

import org.boon.core.reflection.BeanUtils;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.fortech.elasticSearchREST.persistance.VehicleDAOImpl;
import com.google.gson.Gson;

public class DbES {


	private ElasticSearchService elasticSearchService = new ElasticSearchService();
	private VehicleDAO vehicleDAOImpl = new VehicleDAOImpl();

	private 	Gson gson = new Gson();


	public  void transferFromDbToES (String index, String type, String id, String[] tags) throws InterruptedException {
		elasticSearchService.createIndex(index, type, id, gson.toJson(Vehicle.populate
				(vehicleDAOImpl.findVehicleById(Long.parseLong(id)), tags), VehicleES.class));
	}




//	public static void main(String[] args) throws InterruptedException{
//
//		DbES dbES = new DbES();
//		String[] arra = {"new car", "best car"};
//		dbES.createFromDb("vehicles", "vehicle", "4", arra);
//
//		//		 VehicleDAO vehicleDAO = new VehicleDAOImpl();
//		//		vehicleDAO.findVehicleById(3);
//	}





}
