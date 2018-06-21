package com.rest.api.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.api.dao.AccountDetailsDao;
import com.rest.api.models.AccountDetail;

@Repository
public class AccountDetailsDaoImpl implements AccountDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<AccountDetail> getAccountDetailByBankIdAndUserId(Long bankId, String token) {
		Query query = sessionFactory.getCurrentSession().createQuery
				("from " + AccountDetail.class.getName()+" as udd where udd.bankDetails.id =:bankId").setParameter("bankId", bankId);
		List<AccountDetail> details = query.list();
		return details;
	}

	@Override
	public AccountDetail getById(Long id) {
		return (AccountDetail) sessionFactory.getCurrentSession().get(AccountDetail.class, id);
	}

	
}
