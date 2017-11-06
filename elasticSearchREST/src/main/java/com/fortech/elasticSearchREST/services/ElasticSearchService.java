package com.fortech.elasticSearchREST.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;

import com.fortech.elasticSearchREST.model.VehicleES;
import com.google.gson.Gson;

/**
 * This class provides functionality for handling ElasticSearch
 *
 * @author andreig.muresan
 */

public class ElasticSearchService {


	@Inject
	private Client esClient;

	@Inject
	private BulkProcessor  esBulkProcessor;

	private Gson gson = new Gson();

	/*
	 * ************************************************************************************************
	 * ************************************* BULK methods *********************************************
	 * ************************************************************************************************
	 */

	public void bulkAddToIndex(final String indexName, final String typeName, List<VehicleES> vehicleES) {
		String jsonData = gson.toJson(vehicleES);
		vehicleES.forEach(vehicleES1 -> esBulkProcessor.add(new IndexRequest(indexName, typeName, vehicleES1.getId().toString()).source(jsonData)) );
	}

	/**
	 * Adds the given data to the bulk bulk request to process all data and adds to ElasticSaerch.
	 * 
	 * @param index
	 * 			Name of the index.
	 * @param typeName
	 * 			Name of type.
	 * @param vehicleES
	 * 			List of objects. 
	 */
	@SuppressWarnings("unused")
	public void bulkAddIndex(String index, String typeName, List<VehicleES> vehicleES){
		BulkRequestBuilder bulkRequestBuilder = esClient.prepareBulk();
		vehicleES.forEach(vehicleES1 -> bulkRequestBuilder.add(esClient.prepareIndex(index, typeName, vehicleES1.getElasticSearchId())
				.setSource(gson.toJson(vehicleES1))));
		BulkResponse responses = bulkRequestBuilder.get();
	}

	/*
	 * ************************************************************************************************
	 * ********************************* CRUD API methods *********************************************
	 * ************************************************************************************************
	 */

	/**
	 * Adds data to the ElasticSearch
	 *
	 * @param index Name of the index/alias.
	 * @param type  Name of the type.
	 * @param data  Data in JSON format
	 * @throws InterruptedException .
	 */
	public void createIndex(String index, String type, String id, String data) throws InterruptedException {
		esClient.prepareIndex(index, type, id).setSource(data).get();
		Thread.sleep(2000);
	}


	/**
	 * Update data to the ElasticSearch.
	 *
	 * @param index    Name of the index/alias.
	 * @param type     Name of the type.
	 * @param id       Unique ID for document
	 * @param jsonData String in Json format.
	 * @throws InterruptedException .
	 * @throws ExecutionException .
	 */
	@SuppressWarnings("unused")
	public void updateIndex(String index, String type, String id, String jsonData) throws InterruptedException, ExecutionException {
		UpdateResponse response = esClient.prepareUpdate(index, type, id).setDoc(jsonData).execute().get();
	}

	/**
	 * Update data to the ElasticSearch with a specific field.
	 *
	 * @param index    Name of the index/alias.
	 * @param type     Name of the type.
	 * @param id       Unique ID for document
	 * @param field    Name of field
	 * @param newValue Value of field .
	 * @throws InterruptedException .
	 * @throws ExecutionException .
	 */
	@SuppressWarnings("unused")
	public void updateDocument(String index, String type, String id, String field, String newValue) throws InterruptedException, ExecutionException {

		Map<String, Object> updateObject = new HashMap<>();
		updateObject.put(field, newValue);

		UpdateResponse updateRequestBuilder = esClient.prepareUpdate(index, type, id)
				.setDoc(updateObject).execute().get();
	}

	/**
	 * Remove data from the ElasticSearch
	 *
	 * @param index Name of the index/alias.
	 * @param type  Name of the type.
	 * @param id    Unique ID for document
	 */
	public void removeDocument(String index, String type, String id) {
		esClient.prepareDelete(index, type, id).execute().actionGet();
	}

	/**
	 * Simple way to get a document from ElasticSearch.
	 *
	 * @param index Name of index/alias
	 * @param type  Name of type
	 * @param id    Id number as string
	 * @return {@link VehicleES} Object
	 */
	public VehicleES getDocument(String index, String type, String id) {
		GetResponse getResponse = esClient.prepareGet(index, type, id).execute().actionGet();
		String source = getResponse.getSourceAsString();
		return gson.fromJson(source, VehicleES.class);
	}

	/**
	 * Get all Documents from ElasticSearch.
	 * SearchResponse return response as String
	 *
	 * @param indexName Name of index/alias.
	 * @param typeName  Name of type.
	 * @return List<VehicleES>
	 */
	public List<VehicleES> getAllVehicles(String indexName, String typeName) {
		int scrollSize = 1000;
		SearchResponse response = null;
		int i = 0;
		List<String> esData = new ArrayList<>();
		List<VehicleES> vehicles = new ArrayList<>();

		while (response == null || response.getHits().hits().length != 0) {
			esData.clear();
			response = esClient.prepareSearch(indexName).setTypes(typeName).setQuery(QueryBuilders.matchAllQuery())
					.setSize(scrollSize).addSort("id", SortOrder.ASC).setFrom(i * scrollSize).execute().actionGet();
			for (SearchHit hit : response.getHits()) {

				esData.add(hit.getSourceAsString());

			}
			i++;

			for (String source : esData) {
				VehicleES vehicleES = gson.fromJson(source, VehicleES.class);
				vehicles.add(vehicleES);
			}
		}
		return vehicles;
	}

	/**
	 * Search documents with filter.
	 *
	 * @param index Name of the index/alias.
	 * @param type  Name of the type.
	 * @param field Name of field.
	 * @param value Name of field value.
	 * @return List of Vehicle Object in Json data.
	 */
	public List<VehicleES> getAllVehiclesByField(String index, String type, String field, String value){
		int scrollSize = 1000;
		SearchResponse response = null;
		int i = 0;
		List<String> esData = new ArrayList<>();
		List<VehicleES> vehicles = new ArrayList<>();
		QueryBuilder queryBuilder = new MatchQueryBuilder(field, value);

		while (response == null || response.getHits().hits().length != 0) {
			esData.clear();
			response = esClient.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH).setQuery(queryBuilder)
					.setSize(scrollSize).addSort("id", SortOrder.ASC).setFrom(i * scrollSize).execute().actionGet();
			for (SearchHit hit : response.getHits()) {

				esData.add(hit.getSourceAsString());

			}
			i++;

			for (String source : esData) {
				VehicleES vehicleES = gson.fromJson(source, VehicleES.class);
				vehicles.add(vehicleES);
			}
		}
		return vehicles;
	}

	/**
	 * Search document with filter.
	 *
	 * @param index Name of the index/alias.
	 * @param type  Name of the type.
	 * @param field Name of field.
	 * @param value Name of field value.
	 * @return Vehicle Object in Json data.
	 */
	public VehicleES findDocumentByFilter(String index, String type, String field, String value) {
		String stringHit = "";
		QueryBuilder queryBuilder = new MatchQueryBuilder(field, value);
		SearchResponse response = esClient.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(queryBuilder).setFrom(0).setSize(60).setExplain(true).execute().actionGet();
		for (SearchHit hit : response.getHits()) {
			stringHit = hit.getSourceAsString();
		}

		return gson.fromJson(stringHit, VehicleES.class);
	}

	/**
	 * Counts a search result according to field and value.  
	 * 
	 * @param index Name of the index/alias.
	 * @param type  Name of the type.
	 * @param field Name of field.
	 * @param value Value for field.
	 * @return long number.
	 */
	public long searchCounter(String index, String type, String field, String value){
		QueryBuilder queryBuilder = new MatchQueryBuilder(field, value);
		return esClient.prepareSearch(index).setTypes(type).setSize(0).setQuery(queryBuilder).get().getHits().getTotalHits();
	}

	/**
	 * According to field this method return a list of values from that field
	 * 
	 * @param index Name of index/alias.
	 * @param type	Name of type.
	 * @param field	Name of field.
	 * @return list of String values.
	 */
	public List<String> getAllValueOfField(String index, String type, String field){
		List<String> values = new ArrayList<>();
		SearchResponse response = null;

		response = esClient.prepareSearch(index).setTypes(type).setQuery(QueryBuilders.matchAllQuery())
				.addAggregation(AggregationBuilders.terms("agg1").field(field)
						).execute().actionGet();
		Terms agg1 = response.getAggregations().get("agg1");
		for(Bucket value :agg1.getBuckets()){
			values.add(value.getKeyAsString().substring(0, 1).toUpperCase() +value.getKeyAsString().substring(1).toLowerCase());
		}

		return values;
	}
	/**
	 * 
	 * @param index
	 * @param type
	 * @param field
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public List<VehicleES> getAllValueFromESWithMinMaxValueFromAField(String index, String type, String field, Double minValue, Double maxValue){
		int scrollSize = 1000;
		List<String> values = new ArrayList<>();
		List<VehicleES> vehicles = new ArrayList<>();
	
		SearchResponse response = null;
		
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field).gte(minValue).lte(maxValue);
		values.clear();
		response = esClient.prepareSearch(index).setTypes(type).setQuery(rangeQueryBuilder)
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
	
}
