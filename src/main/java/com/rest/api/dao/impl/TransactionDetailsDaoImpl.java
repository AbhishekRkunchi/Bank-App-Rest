package com.rest.api.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.api.dao.TransactionDetailsDao;
import com.rest.api.models.TransactionDetails;

@Repository
public class TransactionDetailsDaoImpl implements TransactionDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public List<TransactionDetails> getTransactionDetailByAccountId(Long accountId) {
		return null;
	}

}
