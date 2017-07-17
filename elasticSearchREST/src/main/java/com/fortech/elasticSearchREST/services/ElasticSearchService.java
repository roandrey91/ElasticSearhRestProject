package com.fortech.elasticSearchREST.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.fortech.elasticSearchREST.model.VehicleES;
import com.google.gson.Gson;

/**
 * This class provides functionality for handling ElasticSearch
 * 
 * @author andreig.muresan
 */

public class ElasticSearchService {

	/*
	 * ************************************************************************************************
	 * ******************************** Configuration parameters **************************************
	 * ************************************************************************************************
	 */

	private static final Logger logger = Logger.getLogger(ElasticSearchService.class.getName());

	private Client esClient;

	private TransportClient transportClient = null;

	/**
	 * Host
	 */
	private static final String ES_HOST = "localhost";

	/**
	 * Port
	 */
	private static final int ES_PORT = 9300;

	/**
	 * Cluster name
	 */
	private static final String ES_CLUSTER_NAME = "elasticsearch";

	private Gson gson = new Gson();

	/*
	 * ************************************************************************************************
	 * ******************************** Initialization/Connection to ES methods ***********************
	 * ************************************************************************************************
	 */

	private static ElasticSearchService instance = null;

	public static ElasticSearchService getInstance() {
		if (instance == null) {
			instance = new ElasticSearchService();
		}
		return instance;
	}


	/**
	 * Deliver connection to ElasticSearch
	 *
	 * @return Current {@link Client} object
	 */
	public Client getESClient() {

		final Settings esSettings = Settings.settingsBuilder().put("cluster.name", ES_CLUSTER_NAME).build();
		try {
			transportClient = TransportClient.builder().settings(esSettings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		esClient = transportClient;

		return esClient;
	}


	/*
	 * ************************************************************************************************
	 * ******************************** CRUD API methods *********************************************
	 * ************************************************************************************************
	 */

	/**
	 * Search for matches documents with @param id 
	 * 
	 * @param id
	 * @return boolean 
	 */

	public boolean isIndexExist(String id) {
		try {
			if (getESClient().admin().indices().prepareExists(id).execute().actionGet().isExists()) {
				return true;
			}
		} catch (Exception exception) {
			logger.severe("a");
		}

		return false;
	}

	/**
	 * Adds data to the ElasticSearch
	 * 
	 * @param index
	 * 		Name of the index/alias.
	 * @param type
	 * 		Name of the type.
	 * @param id
	 * 		Unique ID for document 
	 * @param data
	 * 			Data in JSON format 
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unused")
	public void createIndex(String index, String type, String id, String data) throws InterruptedException {
		IndexResponse response = getESClient().prepareIndex(index, type, id).setSource(data).get();
		Thread.sleep(2000);
	}

	/**
	 * Update data to the ElasticSearch.
	 * 
	 * @param index
	 * 		Name of the index/alias.
	 * @param type
	 * 		Name of the type.
	 * @param id
	 * 		Unique ID for document 
	 * @param jsonData
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unused")
	public void updateIndex(String index, String type, String id, String jsonData) throws InterruptedException, ExecutionException {
		UpdateResponse response = getESClient().prepareUpdate(index, type, id).setDoc(jsonData).execute().get();
	}

	/**
	 * Update data to the ElasticSearch.
	 * 
	 * @param index
	 * 		Name of the index/alias.
	 * @param type
	 * 		Name of the type.
	 * @param id
	 * 		Unique ID for document 
	 * @param field
	 * 		Name of field 
	 * @param newValue
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unused")
	public  void updateDocument(String index, String type, String id, String field, String newValue) throws InterruptedException, ExecutionException {

		Map<String, Object> updateObject = new HashMap<String, Object>();
		updateObject.put(field, newValue);

		UpdateResponse updateRequestBuilder = getESClient().prepareUpdate(index, type, id)
				.setDoc(updateObject).execute().get();

	}
	/**
	 * Remove data from the ElasticSearch
	 * 
	 * @param index
	 * 		Name of the index/alias.
	 * @param type
	 * 		Name of the type.
	 * @param id
	 * 		Unique ID for document 
	 */
	
	@SuppressWarnings("unused")
	public void removeDocument(String index, String type, String id) {
		DeleteResponse response = getESClient().prepareDelete(index, type, id).execute().actionGet();
	}

	/**
	 * Get document from ElasticSearch with Input parameters 
	 *  
	 * @param index
	 * 			Name of index/alias
	 * @param type
	 * 			Name of type
	 * @param id
	 * 			Id number as string
	 * @return {@link VehicleES} Object
	 */

	public VehicleES getDocument(String index, String type, String id) {

		GetResponse getResponse = getESClient().prepareGet(index, type, id).execute().actionGet();
		Map<String, Object> source = getResponse.getSource();

		VehicleES vehicleES = new VehicleES();
		vehicleES.setId(Long.parseLong(getResponse.getId()));
		vehicleES.setBrandName(source.get("brandName").toString());
		vehicleES.setBodyType(source.get("bodyType").toString());
		vehicleES.setFuelType(source.get("fuelType").toString());
		vehicleES.setTransmission(source.get("transmission").toString());
		vehicleES.setRegistracionDate(source.get("registracionDate").toString());
		vehicleES.setColor(source.get("color").toString());
		vehicleES.setPrice(Double.parseDouble(source.get("price").toString()));
		vehicleES.setVehicleLocation(source.get("vehicleLocation").toString());

		String tags = source.get("tags").toString();
		String[] array = tags.split("\\[]");
		vehicleES.setTags(array);

		System.out.println("------------------------------");
		System.out.println("Index: " + getResponse.getIndex());
		System.out.println("Type: " + getResponse.getType());
		System.out.println("Id: " + getResponse.getId());
		System.out.println(source);
		System.out.println("------------------------------");

		return vehicleES;

	}

	/**
	 * Simple way to get a document from ElasticSearch. 
	 *  
	 * @param index
	 * 			Name of index/alias
	 * @param type
	 * 			Name of type
	 * @param id
	 * 			Id number as string
	 * @return {@link VehicleES} Object
	 */

	public VehicleES getDocumentGson(String index, String type, String id) {

		GetResponse getResponse = getESClient().prepareGet(index, type, id).execute().actionGet();

		String source = getResponse.getSourceAsString();

		VehicleES vehicleES = gson.fromJson(source, VehicleES.class);

		return vehicleES;
	}

	/**
	 * Get all Documents from ElasticSearch.
	 * SearchResponse return a List<Map<String, Object>>
	 * 
	 * @param indexName
	 * 			Name of index/alias.
	 * @param typeName
	 * 			Name of type.
	 * @return List<VehicleES> 
	 */

	public List<VehicleES> getAll(String indexName, String typeName) {
		int scrollSize = 1000;
		SearchResponse response = null;
		int i = 0;
		List<Map<String, Object>> esData = new ArrayList<Map<String, Object>>();
		List<VehicleES> vehicles = new ArrayList<>();

		while (response == null || response.getHits().hits().length != 0) {
			esData.clear();
			response = getESClient().prepareSearch(indexName).setTypes(typeName).setQuery(QueryBuilders.matchAllQuery())
					.setSize(scrollSize).setFrom(i * scrollSize).execute().actionGet();
			for (SearchHit hit : response.getHits()) {
				esData.add(hit.getSource());
			}
			i++;			

			for (Map<String, Object> source : esData) {

				VehicleES vehicleES = new VehicleES();

				vehicleES.setId(Long.parseLong(source.get("id").toString()));
				vehicleES.setBrandName(source.get("brandName").toString());
				vehicleES.setBodyType(source.get("bodyType").toString());
				vehicleES.setFuelType(source.get("fuelType").toString());
				vehicleES.setTransmission(source.get("transmission").toString());
				vehicleES.setRegistracionDate(source.get("registracionDate").toString());
				vehicleES.setColor(source.get("color").toString());
				vehicleES.setPrice(Double.parseDouble(source.get("price").toString()));
				vehicleES.setVehicleLocation(source.get("vehicleLocation").toString());

				String tags = source.get("tags").toString();
				String[] array = tags.split("\\[]");
				vehicleES.setTags(array);

				vehicles.add(vehicleES);
			}			
		}
		return vehicles;
	}


	/**
	 * Get all Documents from ElasticSearch.
	 * SearchResponse return response as String 
	 * 
	 * @param indexName
	 * 			Name of index/alias.
	 * @param typeName
	 * 			Name of type.
	 * @return List<VehicleES> 
	 */

	public List<VehicleES> getAllVehicles(String indexName, String typeName) {
		int scrollSize = 1000;
		SearchResponse response = null;
		int i = 0;
		List<String> esData = new ArrayList<String>();
		List<VehicleES> vehicles = new ArrayList<>();

		while (response == null || response.getHits().hits().length != 0) {
			esData.clear();
			response = getESClient().prepareSearch(indexName).setTypes(typeName).setQuery(QueryBuilders.matchAllQuery())
					.setSize(scrollSize).setFrom(i * scrollSize).execute().actionGet();
			for (SearchHit hit : response.getHits()) {

				esData.add(hit.getSourceAsString());
			}
			i++;			

			for (String source : esData) {
				VehicleES vehicleES =gson.fromJson(source, VehicleES.class);
				vehicles.add(vehicleES);
			}			
		}
		return vehicles;
	}
	
	
	/**
	 * Search document with filter.
	 * 
	 * @param index
	 * 		Name of the index/alias.
	 * @param type
	 * 		Name of the type.
	 * @param field
	 * 		Name of field.
	 * @param value
	 * 		Name of field value.
	 * @return
	 */
	public VehicleES findDocumentByFilter(String index, String type, String field, String value) {

		SearchResponse response = null;
		VehicleES vehicleES = new VehicleES();
		String stringHit = "";

		QueryBuilder queryBuilder = new MatchQueryBuilder(field, value);
		response = getESClient().prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(queryBuilder).setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		for(SearchHit hit : response.getHits()){
			stringHit = hit.getSourceAsString(); 
		}
		vehicleES = gson.fromJson(stringHit, VehicleES.class);
		return vehicleES;
	}

	


	public static Map<String, Object> putJsonDocument(Long id, String brandName, String bodyType, String fuelType,
			String transmission, String registracionDate, String color, Double price, String vehicleLocation,
			String[] tags) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put("id", id);
		jsonDocument.put("brandName", brandName);
		jsonDocument.put("bodyType", bodyType);
		jsonDocument.put("fuelType", fuelType);
		jsonDocument.put("transmission", transmission);
		jsonDocument.put("registracionDate", registracionDate);
		jsonDocument.put("color", color);
		jsonDocument.put("price", price);
		jsonDocument.put("vehicleLocation", vehicleLocation);

		return jsonDocument;
	}

}
