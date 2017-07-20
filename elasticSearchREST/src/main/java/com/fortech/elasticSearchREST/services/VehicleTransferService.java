package com.fortech.elasticSearchREST.services;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.google.gson.Gson;

import javax.inject.Inject;

import java.util.List;


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
        elasticSearchService.createIndex(index, type, id, gson.toJson(VehicleToVehicleES.createNewVehicleESFromVehicle
                (vehicleDAOImpl.findVehicleById(Long.parseLong(id)), tags), VehicleES.class));
    }

    public void transferAllDataFromDbToES(String index, String type, String[] tags) {

        List<Vehicle> vehicleDB = vehicleDAOImpl.readAll();
        List<VehicleES> vehicleESList = VehicleToVehicleES.populateList(vehicleDB, tags);

        elasticSearchService.bulkAddIndex(index, type, vehicleESList);
    }

    public void tranferAllDataFromDbToEs2(String indexName, final String typeName, String[] tags ){
    	
    	List<Vehicle> vehicleDB = vehicleDAOImpl.readAll();
        List<VehicleES> vehicleESList = VehicleToVehicleES.populateList(vehicleDB, tags);
        
        elasticSearchService.bulkAddToIndex(indexName, typeName, vehicleESList);
    }
    
    
    
}
