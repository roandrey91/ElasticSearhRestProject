package com.fortech.elasticSearchREST.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

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

	private static final int    ES_BULK_PROCESSOR_ACTIONS = 10000;

	private static final int    ES_BULK_PROCESSOR_CONCURRENT_REQUESTS = 1;

	private static final int    ES_BULK_PROCESSOR_FLUSH_INTERVAL = 5;

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

}
