package com.rest.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TransactionDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private AccountDetail accountDetail;

	private int tranasctionType;
	
	private Double transactionAmount;
	
	private Double availableBal;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AccountDetail getAccountDetail() {
		return accountDetail;
	}

	public void setAccountDetail(AccountDetail accountDetail) {
		this.accountDetail = accountDetail;
	}

	public int getTranasctionType() {
		return tranasctionType;
	}

	public void setTranasctionType(int tranasctionType) {
		this.tranasctionType = tranasctionType;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getAvailableBal() {
		return availableBal;
	}

	public void setAvailableBal(Double availableBal) {
		this.availableBal = availableBal;
	}
	
}
