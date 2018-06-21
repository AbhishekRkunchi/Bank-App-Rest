package com.rest.api.dao;

import java.util.List;

import com.rest.api.models.BankDetails;

public interface BankDetailsDao {
	
	public List<BankDetails> getallBanks();
	
	public BankDetails getBankDetailById(Long id);

}
