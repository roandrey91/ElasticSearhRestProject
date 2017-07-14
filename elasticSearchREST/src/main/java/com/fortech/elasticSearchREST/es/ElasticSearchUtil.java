package com.fortech.elasticSearchREST.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;

@Singleton
@Startup
public class ElasticSearchUtil {
	
	
//	private Client  	esClient;
//
//	private TransportClient transportClient;
//
//	private BulkProcessor 	esBulkProcessor;
//
//	/*
//	 *  Configuration parameters
//	 */
//
//	//host
//	private static final String ES_HOST 						= "localhost";
//
//	//port
//	private static final int ES_PORT 							= 9300;
//
//	//cluster name 
//	private static final String ES_CLUSTER_NAME			  		= "elasticsearch";
//
//	//bulk request 
//	private final static int ES_BULK_PROCESSOR_ACTIONS 			= 1000;
//
//	//bulk concurrent request
//	private final static int ES_BULK_PROCESSOR_CONCURRENT   	= 1;
//
//	//flusj interval in seconds
//	private static final int ES_BULK_PROCESSOR_FLUSH_INTERVAL   = 5;
//
//	/*
//	 * Inintialization methods
//	 */
//
//	@PostConstruct
//	public void init() throws UnknownHostException {
//		//create a new client
//		final Settings esSettings = Settings.settingsBuilder().put("cluster.name", ES_CLUSTER_NAME).build();
//		transportClient = TransportClient.builder().settings(esSettings).build().
//				addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT));
//		esClient = transportClient;
//
//		//create a new bulk processor
//		esBulkProcessor = initBulkProcessor();
//	}
//
//	public  Client getESClient() {
//		return esClient;
//	}
//	
//	
//
//	private BulkProcessor initBulkProcessor() {
//		return BulkProcessor.builder(esClient, new BulkProcessor.Listener() {
//
//			@Override
//			public void beforeBulk(final long executionId, final BulkRequest request) {
//				// TODO Log if necessary, in AUDIT/FINE level
//				// System.out.println(String.format("beforeBulk(): executionId=%d, request=%s", executionId, request));
//			}
//
//			@Override
//			public void afterBulk(final long executionId, final BulkRequest request, final BulkResponse response) {
//				// TODO Log if necessary, in AUDIT/FINE level
//				// System.out.println(String.format("afterBulk(): executionId=%d, request=%s, response=%s", executionId,
//				// request, response));
//			}
//
//			@Override
//			public void afterBulk(final long executionId, final BulkRequest request, final Throwable failure) {
//				// TODO Log if necessary, in AUDIT/FINE level
//				// System.out.println(String.format("afterBulk(): executionId=%d, request=%s, failure=%s", executionId,
//				// request, failure));
//			}
//
//		}).setBulkActions(ES_BULK_PROCESSOR_ACTIONS).setConcurrentRequests(ES_BULK_PROCESSOR_CONCURRENT)
//				.setFlushInterval(TimeValue.timeValueSeconds(ES_BULK_PROCESSOR_FLUSH_INTERVAL)).build();
//	}
//
//
//	/**
//	 * Clean up before destroying
//	 */
//	@PreDestroy
//	private void preDestroy() {
//
//		// close the bulk processor, this would flushes the open actions
//		esBulkProcessor.close();
//		// close also the client
//		esClient.close();
//	}
////	
////	
	
	
	
	
	
//	

	private static final Logger logger = Logger.getLogger(ElasticSearchUtil.class.getName());
	private static final String CLUSTER_NAME = "elasticsearch";
	private static final String ELASTIC_SERVER_HOST = "localhost";
	private static final int ELASTIC_SERVER_PORT = 9200;
	private static final int ELASTIC_INDEX_PORT = 9300;

	public TransportClient getTransportClient(String cluster, String host, int port) throws UnknownHostException {
		TransportClient transportClient = null;

		try {
			cluster = cluster == null ? CLUSTER_NAME : cluster;
			host = host == null ? ELASTIC_SERVER_HOST : host;
			port = port == 0 ? ELASTIC_SERVER_PORT : port;

			Settings settings = Settings.settingsBuilder()
					.put(ElasticConstants.CLUSTER_NAME, cluster).build();
			transportClient = TransportClient.builder().settings(settings).build().
					addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

		} catch (Exception e) {
			logger.severe("getTransportClient");
		}
		return transportClient ;
	}

	public  Client getESClient() {
		try {
			return getTransportClient(CLUSTER_NAME, ELASTIC_SERVER_HOST, ELASTIC_INDEX_PORT);
		} catch (Exception e) {
			logger.severe("something go wrong");
		}
		return null;
	}

	public List<SearchResponse> getAllMultiResponseHits(MultiSearchResponse MultiSearchResponse) {
		List<SearchResponse> result = new ArrayList<SearchResponse>();
		try {
			for (MultiSearchResponse.Item item : MultiSearchResponse.getResponses()) {
				SearchResponse response = item.getResponse();
				result.add(response);
			}

		} catch (Exception e) {
			logger.severe("getTransportClient");
		}
		return result;
	}
}












