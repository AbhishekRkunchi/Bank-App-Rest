package com.rest.api.response;

public class TransactionDetailResponse {
	
	private Long transactionId;
	
	private int tranasctionType;
	
	private Double transactionAmount;
	
	private Double availableBal;

	
	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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
