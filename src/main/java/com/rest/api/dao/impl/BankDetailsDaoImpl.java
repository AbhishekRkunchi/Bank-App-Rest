package com.rest.api.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.api.dao.BankDetailsDao;
import com.rest.api.models.BankDetails;

@Repository
public class BankDetailsDaoImpl implements BankDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<BankDetails> getallBanks() {
		Query query = sessionFactory.getCurrentSession().createQuery
				("from " + BankDetails.class.getName());
		List<BankDetails> details = query.list();
		return details;
	}

	@Override
	public BankDetails getBankDetailById(Long id) {
	return 	(BankDetails) sessionFactory.getCurrentSession().get(BankDetails.class, id);
	}
	
	

}
