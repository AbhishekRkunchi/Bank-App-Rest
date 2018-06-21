package com.rest.api.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.api.dao.UserDao;
import com.rest.api.models.UserDetails;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveUser(UserDetails users) {
		sessionFactory.getCurrentSession().save(users);	
	}
	
	@Override
	public UserDetails getByToken(String token) {
		Query query = sessionFactory.getCurrentSession().createQuery
				("from UserDetails where authToken =:authToken").setParameter("authToken", token);
		List<UserDetails> details = query.list();
		if(details!=null && !details.isEmpty()) {
			return details.get(0);
		}
		return null;
	}

}
