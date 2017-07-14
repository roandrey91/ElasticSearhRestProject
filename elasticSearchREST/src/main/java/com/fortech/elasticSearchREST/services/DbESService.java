package com.fortech.elasticSearchREST.services;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.fortech.elasticSearchREST.persistance.VehicleDAOImpl;
import com.google.gson.Gson;
/**
 * This class provide transfer of data between MySQL to ElasticSearch.
 * 
 * @author andreig.muresan
 *
 */

public class DbESService {

	private ElasticSearchService elasticSearchService = new ElasticSearchService();
	private VehicleDAO vehicleDAOImpl = new VehicleDAOImpl();

	private 	Gson gson = new Gson();
		
	/**
	 * Transfer data between MySQL and ElasticSearch.
	 * 
	 * @param index
	 * 		Name of index.
	 * @param type
	 * 		Name of type.
	 * @param id
	 * 		Id number.
	 * @param tags
	 * 		Array of tags.
	 * @throws InterruptedException
	 */
	public  void transferFromDbToES (String index, String type, String id, String[] tags) throws InterruptedException {
		elasticSearchService.createIndex(index, type, id, gson.toJson(Vehicle.populate
				(vehicleDAOImpl.findVehicleById(Long.parseLong(id)), tags), VehicleES.class));
	}

}
