package com.fortech.elasticSearchREST.services;

import java.util.List;

import javax.inject.Inject;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.google.gson.Gson;


/**
 * This class provide transfer of data between MySQL to ElasticSearch.
 *
 * @author andreig.muresan
 */

public class VehicleTransferService {

	@Inject
	private ElasticSearchService elasticSearchService;

	@Inject
	private VehicleDAO vehicleDAOImpl;

	private Gson gson = new Gson();

	/**
	 * Transfer data between MySQL and ElasticSearch.
	 *
	 * @param index Name of index.
	 * @param type  Name of type.
	 * @param id    Id number.
	 * @param tags  Array of tags.
	 * @throws InterruptedException .
	 */
	public void transferFromDbToES(String index, String type, String id, String[] tags) throws InterruptedException {
		Vehicle vehicle = vehicleDAOImpl.findVehicleById(Long.parseLong(id));
		String createdId = VehicleToVehicleES.createId(vehicle.getId().toString(), vehicle.getRegistracionDate());
		
		elasticSearchService.createIndex(index, type, createdId,
							 gson.toJson(VehicleToVehicleES.createNewVehicleESFromVehicle
				                        (vehicle, createdId, tags), VehicleES.class));
	}

	/**
	 * This method transfer all data from MySQL to ElasticSearch
	 * 
	 * @param index
	 * 		Name of index.
	 * @param type
	 * 		Name of type.
	 * @param tags
	 * 		Array of Strings - here are tags for short time...
	 */
	public void transferAllDataFromDbToES(String index, String type, String[] tags) {
		List<Vehicle> vehicleDB = vehicleDAOImpl.readAll();
		List<VehicleES> vehicleESList = VehicleToVehicleES.populateList(vehicleDB, tags);
		
		elasticSearchService.bulkAddIndex(index, type, vehicleESList);
	}

	/**
	 * This does't work ... bulkProcess is null 
	 * 
	 * @param indexName
	 * @param typeName
	 * @param tags
	 */
	public void tranferAllDataFromDbToEs2(String indexName, final String typeName, String[] tags ){
		List<Vehicle> vehicleDB = vehicleDAOImpl.readAll();
		List<VehicleES> vehicleESList = VehicleToVehicleES.populateList(vehicleDB, tags);

		elasticSearchService.bulkAddToIndex(indexName, typeName, vehicleESList);
	}

}
