package com.rest.api.service;

import java.util.List;

import com.rest.api.form.UserRequestForm;
import com.rest.api.response.AbstractBankResponse;
import com.rest.api.response.AccountDetailResponse;
import com.rest.api.response.BankDetailResponse;
import com.rest.api.response.TransactionDetailResponse;
import com.rest.api.response.UserResponse;

public interface UserService {
	//Create A User
	public UserResponse saveUser(UserRequestForm userForm);
	
	//Get list of Bank Detail
	public List<AbstractBankResponse> getAllBankDetails();
	
	//Meta Data of Bank with his credentials if present
	public BankDetailResponse getBankDetailById(Long id, String token);
	
	//get His Bank Account Details by id
	public List<AccountDetailResponse> getAccountDetailById(Long bankId, String token);
	
	// Transaction Details for the Account passed
	public List<TransactionDetailResponse> getTranactionByAccountId(Long accountId);

	public boolean validateUserBank(String username, String password, Long bankId);

	public boolean validateUserByToken(String token);
	
}
