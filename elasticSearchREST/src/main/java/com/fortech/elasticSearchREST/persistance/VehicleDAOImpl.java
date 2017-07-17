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

@Stateless
public class VehicleDAOImpl implements VehicleDAO  {

	//private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("elasticSearchREST");

	@PersistenceContext
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

	//	@Override
	//	public void saveVehicle (long id, String brandName, String bodyType, String fuelType, String transmission,
	//			String registracionDate, String color, double price, String vehicleLocation) {
	//
	//		// Create an EntityManager
	//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
	//		EntityTransaction transaction = null;
	//
	//		try {
	//			// Get a transaction
	//			transaction = manager.getTransaction();
	//			// Begin the transaction
	//			transaction.begin();
	//
	//			// Create a new Vehicle object
	//			Vehicle stu = new Vehicle();
	//			stu.setId(id);
	//			stu.setBrandName(brandName);
	//			stu.setBodyType(bodyType);
	//			stu.setFuelType(fuelType);
	//			stu.setTransmission(transmission);
	//			stu.setRegistracionDate(registracionDate);
	//			stu.setColor(color);
	//			stu.setPrice(price);
	//			stu.setVehicleLocation(vehicleLocation);
	//
	//			// Save the Vehicle object
	//			manager.persist(stu);
	//
	//			// Commit the transaction
	//			transaction.commit();
	//		} catch (Exception ex) {
	//			// If there are any exceptions, roll back the changes
	//			if (transaction != null) {
	//				transaction.rollback();
	//			}
	//			// Print the Exception
	//			ex.printStackTrace();
	//		} finally {
	//			// Close the EntityManager
	//			manager.close();
	//		}
	//	}

	@Override
	public void deleteVehicle(long id){
		 Vehicle findVehicle = entityManager.find(Vehicle.class, id);
		entityManager.remove(findVehicle);
		//		// Create an EntityManager
		//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		//		EntityTransaction transaction = null;
		//
		//		try {
		//			// Get a transaction
		//			transaction = manager.getTransaction();
		//			// Begin the transaction
		//			transaction.begin();
		//
		//			// Get the Vehicle object
		//			Vehicle stu = manager.find(Vehicle.class, id);
		//
		//			// Delete the Vehicle
		//			manager.remove(stu);
		//
		//			// Commit the transaction
		//			transaction.commit();
		//		} catch (Exception ex) {
		//			// If there are any exceptions, roll back the changes
		//			if (transaction != null) {
		//				transaction.rollback();
		//			}
		//			// Print the Exception
		//			ex.printStackTrace();
		//		} finally {
		//			// Close the EntityManager
		//			manager.close();
		//		}
	}

	//	@Override
	//	public void updateVehicle(long id, String brandName, String bodyType, String fuelType, String transmission,
	//			String registracionDate, String color, double price, String vehicleLocation){
	//		// Create an EntityManager
	//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
	//		EntityTransaction transaction = null;
	//
	//		try {
	//			// Get a transaction
	//			transaction = manager.getTransaction();
	//			// Begin the transaction
	//			transaction.begin();
	//
	//			// Get the Vehicle object
	//			Vehicle stu = manager.find(Vehicle.class, id);
	//
	//			// Change the values
	//			stu.setId(id);
	//			stu.setBrandName(brandName);
	//			stu.setBodyType(bodyType);
	//			stu.setFuelType(fuelType);
	//			stu.setTransmission(transmission);
	//			stu.setRegistracionDate(registracionDate);
	//			stu.setColor(color);
	//			stu.setPrice(price);
	//			stu.setVehicleLocation(vehicleLocation);
	//
	//			// Update the Vehicle
	//			manager.persist(stu);
	//
	//			// Commit the transaction
	//			transaction.commit();
	//		} catch (Exception ex) {
	//			// If there are any exceptions, roll back the changes
	//			if (transaction != null) {
	//				transaction.rollback();
	//			}
	//			// Print the Exception
	//			ex.printStackTrace();
	//		} finally {
	//			// Close the EntityManager
	//			manager.close();
	//		}
	//	}

	@Override
	public Vehicle findVehicleById(long id){

//		Vehicle vehicle = null;
//
//		// Create an EntityManager
//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//		EntityTransaction transaction = null;
//
//		try {
//			// Get a transaction
//			transaction = manager.getTransaction();
//			// Begin the transaction
//			transaction.begin();
//
//			//Find Vehicle object
//			vehicle = manager.find(Vehicle.class, id);
//
//			// Commit the transaction
//			transaction.commit();
//		}
//		catch (Exception ex) {
//			// If there are any exceptions, roll back the changes
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			// Print the Exception
//			ex.printStackTrace();
//		} finally {
//			// Close the EntityManager
//			manager.close();
//		}
		return entityManager.find(Vehicle.class, id);	
	}

	@Override
	public List<Vehicle> readAll() {
//		List<Vehicle> vehicles = null;
//
//		// Create an EntityManager
//		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//		EntityTransaction transaction = null;
//
//		try {
//			// Get a transaction
//			transaction = manager.getTransaction();
//			// Begin the transaction
//			transaction.begin();
//
//			// Get a List of Vehicles
//			vehicles = manager.createQuery("SELECT s FROM Vehicle s",
//					Vehicle.class).getResultList();
//
//			// Commit the transaction
//			transaction.commit();
//		} catch (Exception ex) {
//			// If there are any exceptions, roll back the changes
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			// Print the Exception
//			ex.printStackTrace();
//		} finally {
//			// Close the EntityManager
//			manager.close();
//		}
		
		List<Vehicle> vehicles = entityManager.createQuery("SELECT s FROM Vehicle s",
				Vehicle.class).getResultList();
		return vehicles;
	}



}
