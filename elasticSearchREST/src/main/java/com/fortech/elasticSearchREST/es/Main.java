package com.fortech.elasticSearchREST.es;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.UUID;

import org.boon.json.JsonCreator;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.services.ElasticSearchService;

public class Main {

//	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("elasticSearchREST");

//	
//
    public static void main(String[] args) {
    	
     ElasticSearchService elasticSearchService = new ElasticSearchService();
    	elasticSearchService.getDocument("vehicles", "product", "1001");
    	
    }
    
//    
//    public static void create(long id, String brandName, String bodyType, String fuelType, String transmission,
//			String registracionDate, String color, double price, String vehicleLocation) {
//        // Create an EntityManager
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            // Get a transaction
//            transaction = manager.getTransaction();
//            // Begin the transaction
//            transaction.begin();
//
//            // Create a new Student object
//            Vehicle stu = new Vehicle();
//            stu.setId(id);
//            stu.setBrandName(brandName);
//            stu.setBodyType(bodyType);
//            stu.setFuelType(fuelType);
//            stu.setTransmission(transmission);
//            stu.setRegistracionDate(registracionDate);
//            stu.setColor(color);
//            stu.setPrice(price);
//            stu.setVehicleLocation(vehicleLocation);
//
//            // Save the student object
//            manager.persist(stu);
//
//            // Commit the transaction
//            transaction.commit();
//        } catch (Exception ex) {
//            // If there are any exceptions, roll back the changes
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            // Print the Exception
//            ex.printStackTrace();
//        } finally {
//            // Close the EntityManager
//            manager.close();
//        }
//    }
	
	
	
	
		
}
