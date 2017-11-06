package com.fortech.elasticSearchREST.persistance;

import java.util.List;

public interface Repository<T> {

	void create(T item);

	void update(T item);

	List<T> readAll();

	T findObject(Object id);

	void delete(T item);

}
