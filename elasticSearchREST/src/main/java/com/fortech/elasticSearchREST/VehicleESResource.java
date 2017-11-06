package com.fortech.elasticSearchREST;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.services.ElasticSearchService;
import com.google.gson.Gson;

/**
 * Resource (exposed at "elasticSearch" path)
 * This class provides all RESTful Services for ElasticSearch 
 * CRUD operations 
 * 
 * @author andreig.muresan
 *
 */

@Path("elasticSearch")
public class VehicleESResource {

	private Gson gson = new Gson();

	@Inject
	private ElasticSearchService elasticSearchService;

	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	@Path("delete/{index}/{type}/{id}")
	public Response removeVehicle(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id)
	{
		elasticSearchService.removeDocument(index, type, id);
		String message = " deleted successful";
		return Response.ok().entity(message).build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("update/{index}/{type}/{id}/{fieldName}/{fieldValue}")
	public Response updateVehicleByField(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id, @PathParam("fieldName") String fieldName, @PathParam("fieldValue") String fieldValue) throws InterruptedException, ExecutionException
	{
		elasticSearchService.updateDocument(index, type, id, fieldName, fieldValue);
		return Response.ok().build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("update/{index}/{type}/{id}")
	public Response updateVehicle(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id, VehicleES vehicle) throws InterruptedException, ExecutionException 
	{
		elasticSearchService.updateIndex(index, type, id, gson.toJson(vehicle, VehicleES.class));
		return Response.ok().build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("add/{index}/{type}")
	public Response addVehicle(@PathParam("index") String index, @PathParam("type") String type,
			VehicleES vehicle) throws InterruptedException 
	{
		String esId = VehicleToVehicleES.createId(vehicle.getId().toString(), vehicle.getRegistracionDate());
		vehicle.setElasticSearchId(esId);
		elasticSearchService.createIndex(index, type, esId, gson.toJson(vehicle, VehicleES.class));
		return Response.ok().build();
	}

	@GET
	@Produces("application/json")
	@Path("{index}/{type}/{id}")
	public Response getVehicle(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id) 
	{
		return Response.ok().entity(elasticSearchService.getDocument(index, type, id)).build();
	}

	@GET
	@Produces("application/json")
	@Path("{index}/{type}/{field}/{fieldValue}")
	public Response getVehicleWihtFilter(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("field") String field, @PathParam("fieldValue") String fieldValue) {
		return Response.ok().entity(elasticSearchService.findDocumentByFilter(index, type, field, fieldValue)).build();
	}

	@GET
	@Produces("application/json")
	@Path("getall/{index}/{type}/{field}/{fieldValue}")
	public List<VehicleES> getVehicleByFilter(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("field") String field, @PathParam("fieldValue") String fieldValue) {
		return elasticSearchService.getAllVehiclesByField(index, type, field, fieldValue);
	}

	@GET
	@Produces("application/json")
	@Path("{index}/{type}")
	public List<VehicleES> getVehicles(@PathParam("index") String index, @PathParam("type") String type) {
		return elasticSearchService.getAllVehicles(index, type);
	}

	@GET
	@Produces("application/json")
	@Path("count/{index}/{type}/{field}/{value}")
	public Response getSearchCount(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("field") String field, @PathParam("value") String value)
	{
		Long countNumber = elasticSearchService.searchCounter(index, type, field, value);
		return Response.ok().entity(gson.toJson(countNumber)).build();
	}

	@GET
	@Produces("application/json")
	@Path("fieldvalue/{index}/{type}/{field}")
	public List<String> getValueOfField(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("field") String field ) {
		return elasticSearchService.getAllValueOfField(index, type, field);
	}
	
	@GET
	@Produces("application/json")
	@Path("findMinMaxValue/{index}/{type}/{field}")
	public List<VehicleES> getValueFromESWithMinMaxQueryFromASpecifiedField(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("field") String field, @QueryParam("minValue") Double minValue, @QueryParam("maxValue") Double maxValue ) {
				
		return elasticSearchService.getAllValueFromESWithMinMaxValueFromAField(index, type, field, minValue, minValue);
	}
}
