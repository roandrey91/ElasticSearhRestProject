package com.fortech.elasticSearchREST.persistance;

import java.io.Serializable;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.fortech.elasticSearchREST.model.Vehicle;

/**
 * This class provides implementation of methods
 * CRUD operations for DB
 *
 * @author andreig.muresan
 */
@Transactional
public class VehicleDAOImpl implements VehicleDAO, Serializable {

    @PersistenceContext(unitName = "vehicle-rules")
    private EntityManager entityManager;

    @Override
    public void saveVehicle(Vehicle vehicle) {
        entityManager.persist(vehicle);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        entityManager.merge(vehicle);
    }

    @Override
    public void deleteVehicle(long id) {
        Vehicle findVehicle = entityManager.find(Vehicle.class, id);
        entityManager.remove(findVehicle);
    }

    @Override
    public Vehicle findVehicleById(long id) {
        return entityManager.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> readAll() {
        return entityManager.createQuery("SELECT s FROM Vehicle s", Vehicle.class).getResultList();
    }

}
