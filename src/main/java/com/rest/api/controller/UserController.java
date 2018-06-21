package com.rest.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.api.form.AccountDetailRequestForm;
import com.rest.api.form.UserRequestForm;
import com.rest.api.response.APIResponse;
import com.rest.api.response.AbstractBankResponse;
import com.rest.api.response.AccountDetailResponse;
import com.rest.api.response.BankDetailResponse;
import com.rest.api.response.TransactionDetailResponse;
import com.rest.api.response.UserResponse;
import com.rest.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/user")
@Api(value = "UserController", description = "Bank App Controller")
public class UserController {

	private static final String UNAUTHORIZED_MESSAGE = "UN-AUTHORIZED TO PERFORM THIS OPERATION";
	private static final String UNAUTHORIZED_STATUS = "1001";

	private static final String ERROR_MESSAGE = "ERROR WHILE DOING OPERATION";
	private static final String ERROR_STATUS = "500";

	@Autowired
	private UserService userService;
	
	@ApiOperation(tags = "USER REST API", httpMethod = "POST", 
			value = "Create User", notes = "Api to create User", 
			response = APIResponse.class, nickname = "create-user")
	@RequestMapping(path = "/create-user", method = RequestMethod.POST,produces = "application/json; charset=UTF-8")
	public @ResponseBody APIResponse registerUser(@RequestBody UserRequestForm user) {
		APIResponse apiResponse = new APIResponse();
		UserResponse userResponse = null;
		try {
			userResponse = userService.saveUser(user);
			apiResponse.setSuccessResult(userResponse);
		}catch (Exception e) {
			e.printStackTrace();
			apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
		}

		return apiResponse;
	}

	@ApiOperation(tags = "BANK REST API", httpMethod = "GET", 
			value = "Bank List", notes = "Api to get Bank List", 
			response = APIResponse.class, nickname = "bank-list")
	@RequestMapping(path = "/bank-list", method = RequestMethod.GET ,produces = "application/json; charset=UTF-8")
	public @ResponseBody APIResponse bankList(@RequestHeader String authToken) {
		APIResponse apiResponse = new APIResponse();
		try {
			if(validateUser(authToken)) {
				List<AbstractBankResponse> bankResponses = userService.getAllBankDetails();
				apiResponse.setSuccessResult(bankResponses);
			}else {
				apiResponse.setFailedResult(UNAUTHORIZED_STATUS,UNAUTHORIZED_MESSAGE);
			}

		}catch (Exception e) {
			e.printStackTrace();
			apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
		}

		return apiResponse;
	}
	@ApiOperation(tags = "BANK REST API", httpMethod = "GET", 
			value = "Bank Meta Data", notes = "Api to get Bank Data", 
			response = APIResponse.class, nickname = "bank-meta-data")
	@RequestMapping(path = "/bank-details", method = RequestMethod.GET ,produces = "application/json; charset=UTF-8")
	public @ResponseBody APIResponse getBankDetails(@RequestHeader String authToken, @RequestParam Long bankId) {
		APIResponse apiResponse = new APIResponse();
		BankDetailResponse bankDetailResponse = null;
		try {
			if(validateUser(authToken)) {
				if(bankId!=null) {
					bankDetailResponse = userService.getBankDetailById(bankId, authToken);
					apiResponse.setSuccessResult(bankDetailResponse);
				}else {
					apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
				}
			}else {
				apiResponse.setFailedResult(UNAUTHORIZED_STATUS,UNAUTHORIZED_MESSAGE);
			}

		}catch (Exception e) {
			e.printStackTrace();
			apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
		}

		return apiResponse;
	}
	
	@ApiOperation(tags = "ACCOUNT REST API", httpMethod = "POST", 
			value = "Account List", notes = "Api to get Account List", 
			response = APIResponse.class, nickname = "account-list")
	@RequestMapping(path = "/account-details", method = RequestMethod.POST ,produces = "application/json; charset=UTF-8")
	public @ResponseBody APIResponse getAccountDetails(@RequestHeader String authToken, @RequestBody AccountDetailRequestForm accountDetailRequestForm) {
		APIResponse apiResponse = new APIResponse();
		List<AccountDetailResponse> accountDetailResponse = new ArrayList<>();
		try {
			if(validateUser(authToken)) {
				if(accountDetailRequestForm!=null && accountDetailRequestForm.getBankId()!=null 
						&& accountDetailRequestForm.getUserName()!=null && accountDetailRequestForm.getPassword()!=null
						&& validateBankLogin(accountDetailRequestForm.getUserName(), accountDetailRequestForm.getPassword(), accountDetailRequestForm.getBankId())) {
					accountDetailResponse = userService.getAccountDetailById(accountDetailRequestForm.getBankId(), authToken);
					apiResponse.setSuccessResult(accountDetailResponse);
				}else {
					apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
				}
			}else {
				apiResponse.setFailedResult(UNAUTHORIZED_STATUS,UNAUTHORIZED_MESSAGE);
			}

		}catch (Exception e) {
			e.printStackTrace();
			apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
		}

		return apiResponse;
	}
	
	@ApiOperation(tags = "TRANSACTION REST API", httpMethod = "GET", 
			value = "Transaction List", notes = "Api to get Transaction List", 
			response = APIResponse.class, nickname = "transaction-details")
	@RequestMapping(path = "/transaction-details", method = RequestMethod.GET ,produces = "application/json; charset=UTF-8")
	public @ResponseBody APIResponse getAccountDetailList(@RequestHeader String authToken, @RequestParam Long accountId) {
		APIResponse apiResponse = new APIResponse();
		List<TransactionDetailResponse> transactionDetailResponses = new ArrayList<>();
		try {
			if(validateUser(authToken)) {
				if(accountId!=null) {
					transactionDetailResponses = userService.getTranactionByAccountId(accountId);
					apiResponse.setSuccessResult(transactionDetailResponses);
				}else {
					apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
				}

			}else {
				apiResponse.setFailedResult(UNAUTHORIZED_STATUS,UNAUTHORIZED_MESSAGE);
			}

		}catch (Exception e) {
			e.printStackTrace();
			apiResponse.setFailedResult(ERROR_STATUS, ERROR_MESSAGE);
		}

		return apiResponse;
	}





	public  boolean validateUser(String token) {
		return userService.validateUserByToken(token);
	}
	
	public  boolean validateBankLogin(String username, String password, Long bankId) {
		return userService.validateUserBank( username,  password, bankId);

	}

}
