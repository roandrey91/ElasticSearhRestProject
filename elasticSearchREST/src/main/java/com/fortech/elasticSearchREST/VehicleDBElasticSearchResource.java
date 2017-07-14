package com.fortech.elasticSearchREST;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.fortech.elasticSearchREST.persistance.VehicleDAOImpl;
import com.fortech.elasticSearchREST.services.DbES;
import com.fortech.elasticSearchREST.services.ElasticSearchService;
import com.google.gson.Gson;

@Path("dbes")
public class VehicleDBElasticSearchResource {


	DbES dbs = new DbES();


	private VehicleDAO vehicleDAOImpl = new VehicleDAOImpl();


	@GET
	@Produces("application/json")
	@Path("transfer/{index}/{type}/{id}")
	public Response getVehicle(@PathParam("id") long id) {
		if (id == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Vehicle vehicle = vehicleDAOImpl.findVehicleById(id);
		if (vehicle == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(vehicle).build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("transfer/{index}/{type}/{id}")
	public Response transferVehicleFromDBtoES(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id) throws InterruptedException 
	{
		String[] string = {"test", "test1", "test2"};
		dbs.transferFromDbToES(index, type, id, string);
		return Response.ok().build();
	}




}
