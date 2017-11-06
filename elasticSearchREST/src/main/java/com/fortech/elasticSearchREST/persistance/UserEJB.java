package com.fortech.elasticSearchREST.persistance;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.fortech.elasticSearchREST.model.Group;
import com.fortech.elasticSearchREST.model.User;

@Stateless
public class UserEJB {


	//@PersistenceContext(unitName="user-rules")
	private EntityManager entityManager;

	public User createUser(User user){
		try {
			user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
		}

		Group group = new Group();
		group.setEmail(user.getEmail());
		group.setGroupname(Group.USERS_GROUP);

		entityManager.persist(user);
		entityManager.persist(group);

		return user;
	}

	public User findUserById(String id){
		TypedQuery<User> query = entityManager.createNamedQuery("findUserByID", User.class);
		query.setParameter("email", id);

		User user = null;
		try {
			user = query.getSingleResult();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}



}
