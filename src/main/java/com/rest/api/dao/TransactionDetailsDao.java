package com.rest.api.dao;

import java.util.List;

import com.rest.api.models.TransactionDetails;

public interface TransactionDetailsDao {
	
	public List<TransactionDetails> getTransactionDetailByAccountId(Long accountId);

}
