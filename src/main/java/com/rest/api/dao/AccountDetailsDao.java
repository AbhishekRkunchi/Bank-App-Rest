package com.rest.api.dao;

import java.util.List;

import com.rest.api.models.AccountDetail;

public interface AccountDetailsDao {

	public AccountDetail getById(Long id);
	public List<AccountDetail> getAccountDetailByBankIdAndUserId(Long bankId, String token);

}
