package com.fortech.elasticSearchREST;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fortech.elasticSearchREST.com.fortech.elasticSearchREST.dbes.VehicleToVehicleES;
import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.model.VehicleES;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.fortech.elasticSearchREST.services.VehicleTransferService;

import java.util.List;

/**
 * Resource (exposed at "dbes" path)
 * 
 * @author andreig.muresan
 *
 */

@Path("dbes")
public class VehicleDBElasticSearchResource {

	@Inject
	private VehicleTransferService vehicleTransferService;

	@Inject
	private VehicleDAO vehicleDAO;
	
	/**
	 * Method handling HTTP GET requests. 
	 * 
	 * @param id
	 * 		Id number.
	 * @return Vehicle object.
	 */
	@GET
	@Produces("application/json")
	@Path("transfer/{index}/{type}/{id}")
	public Response getVehicle(@PathParam("id") long id) {
		if (id == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Vehicle vehicle = vehicleDAO.findVehicleById(id);
		if (vehicle == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(vehicle).build();
	}

	/**
	 * Method handling HTTP POST requests. Transfer data from db
	 * to ElasticSearch.
	 * 
	 * @param index
	 * 		Index name.
	 * @param type
	 * 		Type name.
	 * @param id
	 * 		Id number.
	 * @return Response message
	 * @throws InterruptedException .
	 */
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("transfer/{index}/{type}/{id}")
	public Response transferVehicleFromDBtoES(@PathParam("index") String index, @PathParam("type") String type,
			@PathParam("id") String id) throws InterruptedException 
	{
		String[] string = {"test", "test1", "test2"};
		vehicleTransferService.transferFromDbToES(index, type, id, string);
		return Response.ok().build();
	}

	/**
	 * Method handling HTTP GET request. Get request will receive
	 * a list of Vehicle objects from DB.
	 *
 	 * @return List of Vehicle in Json format.
	 */
	@GET
	@Produces("application/json")
	@Path("transferall/{index}/{type}")
	public List<VehicleES> getAll(){
		String[] string = {"test", "test1", "test2"};
		return 	VehicleToVehicleES.populateList(vehicleDAO.readAll(), string);
	}

	/**
	 * Method handling HTTP POST request. This method will transfer all
	 * data from DB to ElasticSearch.
	 *
	 * @param index
	 * 			Index name.
	 * @param type
	 * 			Type name.
	 * @return	Response message.
	 * @throws InterruptedException .
	 */
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("transferall/{index}/{type}")
	public Response transferAllVehicleFromDBtoES(@PathParam("index") String index, @PathParam("type") String type) throws InterruptedException
	{
		String[] string = {"test", "test1", "test2"};
		vehicleTransferService.transferAllDataFromDbToES(index, type, string);
		return Response.ok().build();
	}

}






