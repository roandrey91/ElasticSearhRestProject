package com.fortech.elasticSearchREST;

import java.util.List;

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
import javax.ws.rs.core.Response.Status;

import com.fortech.elasticSearchREST.model.Vehicle;
import com.fortech.elasticSearchREST.persistance.VehicleDAO;
import com.fortech.elasticSearchREST.persistance.VehicleDAOImpl;
import com.google.gson.Gson;

/**
 *  Resource (exposed at "vehicle" path)
 * This class provide RESTful Web services for MySQL 
 * Get and Post methods for CRUD operations.
 * 
 * @author andreig.muresan
 *
 */
@Path("vehicle")
public class VehicleResource {

	@Inject
	private VehicleDAO vehicleDAO;

	private Gson gson;

	@DELETE
	@Consumes("application/json")
	@Produces("application/json")
	@Path("delete/{vehicleId}")
	public Response deleteVehicle(@PathParam("vehicleId") long vehicleId) {
		vehicleDAO.deleteVehicle(vehicleId);
		return Response.ok().build();
	}

	@POST
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vehicle updateVehicle(Vehicle vehicle) {

//		vehicleDAO.updateVehicle(vehicle.getId(),
//				vehicle.getBrandName(),
//				vehicle.getBodyType(),
//				vehicle.getFuelType(),
//				vehicle.getTransmission(),
//				vehicle.getRegistracionDate(),
//				vehicle.getColor(),
//				vehicle.getPrice(),
//				vehicle.getVehicleLocation());

		return vehicle;
	}

	@POST
	@Path("update")
	@Consumes("application/json")
	@Produces("application/json")
	public Vehicle updateVehicleParams(Vehicle vehicle) {
		vehicleDAO.updateVehicle(vehicle);
		return vehicle;
	}
	
	@POST
	@Path("add")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addVehicle(Vehicle vehicle) {

		vehicleDAO.saveVehicle(vehicle);
		return Response.ok().build();
	}


	@GET
	@Produces("application/json")
	@Path("getAll")
	public List<Vehicle> getVehicles() {
		return vehicleDAO.readAll();
	}

	@GET
	@Produces("application/json")
	@Path("{vehicleId}")
	public Response getVehicle(@PathParam("vehicleId") long vehicleId) {
		if (vehicleId == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Vehicle vehicle = vehicleDAO.findVehicleById(vehicleId);
		if (vehicle == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok().entity(vehicle).build();
	}

}
