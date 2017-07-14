package com.fortech.elasticSearchREST;



import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.primefaces.component.sticky.Sticky;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.services.ElasticSearchService;
import com.google.gson.Gson;

/**
 * This class provides all RESTful Web Services 
 * CRUD operations 
 * 
 * @author andreig.muresan
 *
 */

@Path("elasticSearch")
public class VehicleESResource {

	private Gson gson = new Gson();
	ElasticSearchService elasticSearchService = new ElasticSearchService();


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
	@Path("add/{index}/{type}/{id}")
	public Response addVehicle(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id, VehicleES vehicle) throws InterruptedException 
	{
		elasticSearchService.createIndex(index, type, id, gson.toJson(vehicle, VehicleES.class));
		return Response.ok().build();
	}


	@GET
	@Produces("application/json")
	@Path("findIfExist/{index}")
	public Response ifExist(@PathParam("index") String index) {

		elasticSearchService.isIndexExist(index);

		System.out.println(elasticSearchService.isIndexExist(index));
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
	@Path("{index}/{type}")
	public List<VehicleES> getVehicles(@PathParam("index") String index, @PathParam("type") String type) {
		return elasticSearchService.getAllVehicles(index, type);
	}


}
