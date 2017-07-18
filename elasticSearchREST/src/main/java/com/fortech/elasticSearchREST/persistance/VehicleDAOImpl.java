package com.fortech.elasticSearchREST.persistance;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.fortech.elasticSearchREST.model.Vehicle;

/**
 * This class provides implementation of methods 
 * CRUD operations for DB
 * 
 * @author andreig.muresan
 *
 */

public class VehicleDAOImpl implements VehicleDAO  {

	@PersistenceContext(unitName = "vehicle-rules")
	private EntityManager entityManager;

	@Override
	public void saveVehicle(Vehicle vehicle) {
		if (vehicle.getId() == 0) {
			entityManager.persist(vehicle);
		}else {
			entityManager.merge(vehicle);
		}
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {

		entityManager.merge(vehicle);
	}

	@Override
	public void deleteVehicle(long id){
		 Vehicle findVehicle = entityManager.find(Vehicle.class, id);
		entityManager.remove(findVehicle);
	}

	@Override
	public Vehicle findVehicleById(long id){
		return entityManager.find(Vehicle.class, id);
	}

	@Override
	public List<Vehicle> readAll() {
		List<Vehicle> vehicles = entityManager.createQuery("SELECT s FROM Vehicle s",
				Vehicle.class).getResultList();
		return vehicles;
	}

}
