package com.fortech.elasticSearchREST.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericRepositoryImpl<T> implements Repository<T> {

	private Class<T> persitentClass;
	
	@PersistenceContext(unitName = "vehicle-rules")
	private EntityManager entityManager;

	@Override
	public void create(T item) {
		if(item != null) {
			entityManager.persist(item);
		}
	}

	@Override
	public void update(T item) {
		entityManager.merge(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> readAll() {
		return entityManager.createQuery("Select t from" + persitentClass.getSimpleName() + "t").getResultList();
	}

	@Override
	public T findObject(Object object) {
		final T entity = entityManager.find(persitentClass, object);
		return entity;
	}

	@Override
	public void delete(T item) {
		

	}

}
