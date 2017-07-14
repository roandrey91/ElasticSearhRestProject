package com.fortech.elasticSearchREST.es;


import java.util.List;
import java.util.logging.Logger;

import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;


public class ElasticSearchQueries {


    private static final Logger logger = Logger.getLogger(ElasticSearchQueries.class.getName());
    
    private ElasticSearchUtil elasticSearchUtil;

    public SearchHits findInIndex(String index, String key, String value) {
        try {
            SearchResponse response = elasticSearchUtil.getESClient().prepareSearch(index)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.termQuery(key, value)) // Query
                    //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18)) // Filter
                    .setFrom(0).setSize(60).setExplain(true)
                    .execute()
                    .actionGet();
            return response.getHits();
        } catch (Exception e) {
        	 logger.severe("getTransportClient");
        }
        return null;
    }

    public SearchHits findInCluster(String key, String value) {
        try {
            SearchResponse response = elasticSearchUtil.getESClient().prepareSearch()
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(QueryBuilders.termQuery(key, value)) // Query
                    .execute()
                    .actionGet();
            return response.getHits();
        } catch (Exception e) {
        	 logger.severe("getTransportClient");
        }
        return null;
    }

    public SearchHits findByQuery(QueryBuilder builder) {
        try {
            SearchResponse response = elasticSearchUtil.getESClient().prepareSearch()
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(builder) // Query
                    .execute()
                    .actionGet();
            return response.getHits();
        } catch (Exception exception) {
        	 logger.severe("getTransportClient");
        }
        return null;
    }

    public MultiSearchResponse multiSearch(List<SearchRequestBuilder> searchRequestList) {
        try {
            MultiSearchRequestBuilder builder = elasticSearchUtil.getESClient().prepareMultiSearch();
            for (SearchRequestBuilder requestBuilder : searchRequestList) {
                builder.add(requestBuilder);
            }
            return builder.execute().actionGet();
        } catch (Exception e) {
        	 logger.severe("getTransportClient");
        }
        return null;
    }
    
}
	

