package com.fortech.elasticSearchREST.persistance;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.fortech.elasticSearchREST.model.Vehicle;

@Stateless
public class VehicleDAOImpl implements VehicleDAO  {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("elasticSearchREST");


	
	@Override
	public void saveVehicle (long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, double price, String vehicleLocation) {

		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Create a new Vehicle object
			Vehicle stu = new Vehicle();
			stu.setId(id);
			stu.setBrandName(brandName);
			stu.setBodyType(bodyType);
			stu.setFuelType(fuelType);
			stu.setTransmission(transmission);
			stu.setRegistracionDate(registracionDate);
			stu.setColor(color);
			stu.setPrice(price);
			stu.setVehicleLocation(vehicleLocation);

			// Save the Vehicle object
			manager.persist(stu);

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}

	
	@Override
	public void deleteVehicle(long id){
		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get the Vehicle object
			Vehicle stu = manager.find(Vehicle.class, id);

			// Delete the Vehicle
			manager.remove(stu);

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}


	
	@Override
	public void updateVehicle(long id, String brandName, String bodyType, String fuelType, String transmission,
			String registracionDate, String color, double price, String vehicleLocation){
		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get the Vehicle object
			Vehicle stu = manager.find(Vehicle.class, id);

			// Change the values
			stu.setId(id);
			stu.setBrandName(brandName);
			stu.setBodyType(bodyType);
			stu.setFuelType(fuelType);
			stu.setTransmission(transmission);
			stu.setRegistracionDate(registracionDate);
			stu.setColor(color);
			stu.setPrice(price);
			stu.setVehicleLocation(vehicleLocation);

			// Update the Vehicle
			manager.persist(stu);

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}

	
	@Override
	public Vehicle findVehicleById(long id){

		Vehicle vehicle = null;

		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			//Find Vehicle object
			vehicle = manager.find(Vehicle.class, id);

			// Commit the transaction
			transaction.commit();
		}
		catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
		return vehicle;	
	}


	@Override
	public List<Vehicle> readAll() {
		List<Vehicle> vehicles = null;

		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get a List of Vehicles
			vehicles = manager.createQuery("SELECT s FROM Vehicle s",
					Vehicle.class).getResultList();

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
		return vehicles;
	}

	

}
