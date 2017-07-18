package com.fortech.elasticSearchREST.services;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * This class provide transfer of data between MySQL to ElasticSearch.
 * 
 * @author andreig.muresan
 *
 */

public class DbESService {

	@Inject
	private ElasticSearchService elasticSearchService;

	@Inject
	private VehicleDAO vehicleDAOImpl;

	@SuppressWarnings("unused")
	private VehicleToVehicleES vehicleToVehicleES;
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
	 * @throws InterruptedException .
	 */
	public  void transferFromDbToES (String index, String type, String id, String[] tags) throws InterruptedException {
		elasticSearchService.createIndex(index, type, id, gson.toJson(vehicleToVehicleES.populate
				(vehicleDAOImpl.findVehicleById(Long.parseLong(id)), tags), VehicleES.class));
	}

}
