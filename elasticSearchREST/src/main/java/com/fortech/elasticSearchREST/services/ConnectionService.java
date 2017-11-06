package com.fortech.elasticSearchREST.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.model.VehicleFloatES;
import com.google.gson.Gson;

/**
 * This class provide connection to ElasticSearch. 
 * 
 * @author andreig.muresan
 *
 */
public class ConnectionService {

	private static final Logger LOGGER = Logger.getLogger(ConnectionService.class.getName());

	/*
	 * ************************************************************************************************
	 * ******************************** Configuration parameters **************************************
	 * ************************************************************************************************
	 */

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

	/**
	 * Sets when to flush a new bulk request based on the number of actions currently added.<br>
	 * Defaults to 1000. Can be set to -1 to disable it.
	 */
	private static final int    ES_BULK_PROCESSOR_ACTIONS = 10000;

	/**
	 * Sets the number of concurrent requests allowed to be executed.<br>
	 * A value of 0 means that only a single request will be allowed to be executed.<br>
	 * A value of 1 means 1 concurrent request is allowed to be executed while accumulating new bulk requests.<br>
	 */
	private static final int    ES_BULK_PROCESSOR_CONCURRENT_REQUESTS = 1;

	/**
	 * Flush interval in seconds for the bulk processor
	 */
	private static final int    ES_BULK_PROCESSOR_FLUSH_INTERVAL = 5;

	/*
	 * ************************************************************************************************
	 * ******************************** Init methods **************************************************
	 * ************************************************************************************************
	 */


	/**
	 * Deliver connection to ElasticSearch
	 *
	 * @return Current {@link Client} object
	 */
	@Produces
	private Client getESClient() {
		Client esClient = null;

		final Settings esSettings = Settings.settingsBuilder().put("cluster.name", ES_CLUSTER_NAME).build();
		try {
			esClient =  TransportClient.builder().settings(esSettings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT));
		} catch (UnknownHostException e) {
			LOGGER.severe(e.getMessage());
		}
		return esClient;
	}

	/**
	 * Close the connection to ElasticSearch.
	 * 
	 * @param esClient
	 * 
	 */
	@SuppressWarnings("unused")
	private void closeConnectionToES(@Disposes Client esClient) {
		if (esClient == null) {
			return;
		}
		try {
			esClient.close();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}				
	}


	@Produces
	public BulkProcessor getBulk(){
		return initBulkProcessor();
	}

	private BulkProcessor initBulkProcessor() {
		return BulkProcessor.builder(getESClient(), new BulkProcessor.Listener() {

			@Override
			public void beforeBulk(final long executionId, final BulkRequest request) {

			}

			@Override
			public void afterBulk(final long executionId, final BulkRequest request, final BulkResponse response) {

			}

			@Override
			public void afterBulk(final long executionId, final BulkRequest request, final Throwable failure) {

			}

		}).setBulkActions(ES_BULK_PROCESSOR_ACTIONS)
				.setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setConcurrentRequests(ES_BULK_PROCESSOR_CONCURRENT_REQUESTS)
				.setFlushInterval(TimeValue.timeValueSeconds(ES_BULK_PROCESSOR_FLUSH_INTERVAL))
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100),3))
				.build();
	}

//      Gson gson = new Gson();
//	public List<VehicleES> getAllVehiclesByField(String index, String type, String field, String value, String field1, String value1){
//		int scrollSize = 1000;
//		SearchResponse response = null;
//		int i = 0;
//		List<String> esData = new ArrayList<>();
//		List<VehicleES> vehicles = new ArrayList<>();
//		QueryBuilder queryBuilder = new MatchQueryBuilder(field, value);
//		QueryBuilder queryBuilder2 =  
//			    .must(termQuery("content", "test1"))
//
//		while (response == null || response.getHits().hits().length != 0) {
//			esData.clear();
//			response = getESClient().prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH).setQuery(queryBuilder).setQuery(queryBuilder2)
//					.setSize(scrollSize).addSort("id", SortOrder.ASC).setFrom(i * scrollSize).execute().actionGet();
//			for (SearchHit hit : response.getHits()) {
//
//				esData.add(hit.getSourceAsString());
//
//			}
//			i++;
//
//			for (String source : esData) {
//				VehicleES vehicleES = gson.fromJson(source, VehicleES.class);
//				vehicles.add(vehicleES);
//			}
//		}
//		return vehicles;
//	}
	
	public List<VehicleES> getAllValueFromESWithMinMaxValueFromAField(String index, String type, String field, Double minValue, Double maxValue){
		int scrollSize = 1000;
		List<String> values = new ArrayList<>();
		List<VehicleES> vehicles = new ArrayList<>();
		Gson gson = new Gson();
		SearchResponse response = null;
	
		
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field).gt(minValue).lt(maxValue).gte(minValue).lte(maxValue);
		values.clear();
		response = getESClient().prepareSearch(index).setTypes(type).setQuery(rangeQueryBuilder)
				.setFrom(0).setSize(scrollSize).execute().actionGet();
		System.out.println(response);
		for(SearchHit hit : response.getHits()) {
			values.add(hit.getSourceAsString());
		}
		for (String source : values) {
			VehicleES vehicleES = gson.fromJson(source, VehicleES.class);
			vehicles.add(vehicleES);
		}
		
		return vehicles;
	}
	
	public VehicleFloatES findDocumentByFilter(String index, String type, String field) {
		Gson gson = new Gson();
		String stringHit = "";
	
	    BigDecimal bigDecimal = new BigDecimal("11").multiply(new BigDecimal("1.89"));
	    System.out.println(bigDecimal);
//		Double roundNumber =(double) Math.round(value * 100) / 100;

					
		SearchResponse response = getESClient().prepareSearch(index).setTypes(type).setQuery(QueryBuilders.rangeQuery(field).from(bigDecimal).to(bigDecimal))
				.setFrom(0).setSize(60).execute().actionGet();
		System.out.println(response);
		for (SearchHit hit : response.getHits()) {
			stringHit = hit.getSourceAsString();
		}

		return gson.fromJson(stringHit, VehicleFloatES.class);
	}
	
	public void createIndex(String index, String type, String id, String data) throws InterruptedException {
		getESClient().prepareIndex(index, type, id).setSource(data).get();
		Thread.sleep(2000);
	}

	
	public static void main(String[] args){
		Gson gson = new Gson();
		ConnectionService connectionService = new ConnectionService();
////		List<VehicleES> vehicles = connectionService.getAllValueFromESWithMinMaxValueFromAField("vehicles", "vehicle", "price", 6828.3648 / 3.33 , 5050.78);
//		String[] string = {"test", "test1", "test2"};
//		VehicleFloatES vehicle = new VehicleFloatES();
//		vehicle.setPrice(20.79f);
//		vehicle.setId((long)55);
//		vehicle.setRegistracionDate("Undeva in Balcani");
//		String esId = VehicleToVehicleES.createId(vehicle.getId().toString(), vehicle.getRegistracionDate());
//		vehicle.setElasticSearchId(esId);
//		try {
//			connectionService.createIndex("testvehiclefloat", "vehicle", esId, gson.toJson(vehicle, VehicleFloatES.class));
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		System.out.println(connectionService.findDocumentByFilter("testvehiclefloat", "vehicle", "price"));
	}

}
