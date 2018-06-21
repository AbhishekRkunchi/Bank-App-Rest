package com.rest.api.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rest.api.dao.AccountDetailsDao;
import com.rest.api.dao.BankDetailsDao;
import com.rest.api.dao.TransactionDetailsDao;
import com.rest.api.dao.UserDao;
import com.rest.api.form.UserRequestForm;
import com.rest.api.models.AccountDetail;
import com.rest.api.models.BankDetails;
import com.rest.api.models.TransactionDetails;
import com.rest.api.models.UserDetails;
import com.rest.api.response.AbstractBankResponse;
import com.rest.api.response.AccountDetailResponse;
import com.rest.api.response.BankDetailResponse;
import com.rest.api.response.TransactionDetailResponse;
import com.rest.api.response.UserResponse;
import com.rest.api.service.UserService;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BankDetailsDao bankDetailsDao;

	@Autowired
	private AccountDetailsDao accountDetailsDao;

	@Autowired
	private TransactionDetailsDao transactionDetailsDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserResponse saveUser(UserRequestForm userForm) {
		UserResponse response = new UserResponse();
		if(userForm!=null) {
			String token = UUID.randomUUID().toString();
			UserDetails details = new UserDetails();
			details.setUserName(userForm.getUserName());
			details.setPassword(userForm.getPassword());
			details.setEmail(userForm.getEmail());
			details.setPhone(userForm.getPhone());
			details.setAuthToken(token);
			userDao.saveUser(details);
			details = userDao.getByToken(token);
			response.setUserName(details.getUserName());
			response.setPassword(details.getPassword());
			response.setEmail(details.getEmail());
			response.setPhone(details.getPhone());
			response.setAuthToken(token);
			return response;
		}
		return null;
	}

	@Override
	public List<AbstractBankResponse> getAllBankDetails() {
		List<AbstractBankResponse> list = new ArrayList<>();
		List<BankDetails> bankDetails =  bankDetailsDao.getallBanks();
		AbstractBankResponse bankResponse = null;
		for (BankDetails bankDetails2 : bankDetails) {
			bankResponse = new AbstractBankResponse();
			bankResponse.setId(bankDetails2.getId());
			bankResponse.setBankName(bankDetails2.getName());
			list.add(bankResponse);
		}
		return list;
	}

	@Override
	public BankDetailResponse getBankDetailById(Long id, String token) {
		BankDetailResponse detailResponse = null;
		BankDetails bankDetails = bankDetailsDao.getBankDetailById(id);
		if(bankDetails != null) {
			detailResponse = new BankDetailResponse();
			detailResponse.setId(bankDetails.getId());
			detailResponse.setBankName(bankDetails.getName());
			detailResponse.setIfsc(bankDetails.getIfsc());
			detailResponse.setUserName(bankDetails.getUserName());
			detailResponse.setPassword(bankDetails.getPassword());
		}
		return detailResponse;
	}

	@Override
	public List<AccountDetailResponse> getAccountDetailById(Long bankId, String token) {
		List<AccountDetailResponse> accountDetailResponse = new ArrayList<>();
		List<AccountDetail> accountDetails = accountDetailsDao.getAccountDetailByBankIdAndUserId(bankId, token);
		AccountDetailResponse accountD = null;
		if(accountDetails!=null) {
			for (AccountDetail accountDetail : accountDetails) {
				accountD = new AccountDetailResponse();
				accountD.setAccountNumber(accountDetail.getAccountNumber());
				accountD.setId(accountDetail.getId());
				accountDetailResponse.add(accountD);
			}
		}

		return accountDetailResponse;
	}

	@Override
	public List<TransactionDetailResponse> getTranactionByAccountId(Long accountId) {
		List<TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();
		AccountDetail accountDetail = accountDetailsDao.getById(accountId);
		if(accountDetail!=null && accountDetail.getTransactionDetails()!=null 
				&& !accountDetail.getTransactionDetails().isEmpty()) {
			Set<TransactionDetails> transactionDetails = accountDetail.getTransactionDetails();
			TransactionDetailResponse trResponse = null;
			for (TransactionDetails transactionDetail : transactionDetails) {
				trResponse = new TransactionDetailResponse();
				trResponse.setTransactionId(transactionDetail.getId());
				trResponse.setTranasctionType(transactionDetail.getTranasctionType());
				trResponse.setTransactionAmount(transactionDetail.getTransactionAmount());
				trResponse.setAvailableBal(transactionDetail.getAvailableBal());
				transactionDetailResponses.add(trResponse);
			}
		}
		return transactionDetailResponses;

	}

	@Override
	public boolean validateUserBank(String username, String password, Long bankId) {
		BankDetails bankDetails = bankDetailsDao.getBankDetailById(bankId);
		if(bankDetails!=null && bankDetails.getUserName().equalsIgnoreCase(username) 
				&& bankDetails.getPassword().equals(password)) {
			return true;	
		}
		return false;
	}

	@Override
	public boolean validateUserByToken(String token) {
		UserDetails userDetails = userDao.getByToken(token);
		if(userDetails!=null) {
			return true;
		}
		return false;
	}


}
