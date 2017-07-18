package com.fortech.elasticSearchREST.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    @SuppressWarnings("FieldCanBeLocal")
    private Client esClient;

    private TransportClient transportClient;

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

    /**
     * Deliver connection to ElasticSearch
     *
     * @return Current {@link Client} object
     */
    private Client getESClient() {

        final Settings esSettings = Settings.settingsBuilder().put("cluster.name", ES_CLUSTER_NAME).build();
        try {
            transportClient = TransportClient.builder().settings(esSettings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT));
        } catch (UnknownHostException e) {
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
     * Adds data to the ElasticSearch
     *
     * @param index Name of the index/alias.
     * @param type  Name of the type.
     * @param id    Unique ID for document
     * @param data  Data in JSON format
     * @throws InterruptedException .
     */
    @SuppressWarnings("unused")
    public void createIndex(String index, String type, String id, String data) throws InterruptedException {
        IndexResponse response = getESClient().prepareIndex(index, type, id).setSource(data).get();
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
        UpdateResponse response = getESClient().prepareUpdate(index, type, id).setDoc(jsonData).execute().get();
    }

    /**
     * Update data to the ElasticSearch.
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

        UpdateResponse updateRequestBuilder = getESClient().prepareUpdate(index, type, id)
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
         getESClient().prepareDelete(index, type, id).execute().actionGet();
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
        GetResponse getResponse = getESClient().prepareGet(index, type, id).execute().actionGet();
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
            response = getESClient().prepareSearch(indexName).setTypes(typeName).setQuery(QueryBuilders.matchAllQuery())
                    .setSize(scrollSize).setFrom(i * scrollSize).execute().actionGet();
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
        SearchResponse response = getESClient().prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(queryBuilder).setFrom(0).setSize(60).setExplain(true).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
            stringHit = hit.getSourceAsString();
        }

        return gson.fromJson(stringHit, VehicleES.class);
    }

}
