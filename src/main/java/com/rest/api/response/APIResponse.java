package com.rest.api.response;

import java.util.HashMap;
import java.util.Map;

public class APIResponse {


	/** The success object. */
	private Map<String, Object> successObject = new HashMap<String, Object>();

	/** The failed object. */
	private Map<String, Object> failedObject = new HashMap<String, Object>();

	public void setSuccessResult(Object object) {
		successObject.put("status_message", "SUCCESS");
		successObject.put("status_code", 200);
		if (object != null) {
			successObject.put("result", object);
		}
	}

	public Map<String, Object> getSuccessObject() {
		return successObject;
	}

	public void setFailedResult(int statusCode, String statusMessage, Object object) {

		failedObject.put("status_message", statusMessage);
		failedObject.put("status_code", statusCode);
		if (object != null) {
			failedObject.put("result", object);
		}

	}

	public void setFailedResult(String statusCode, String statusMessage) {

		failedObject.put("status_message", statusMessage);
		failedObject.put("status_code", statusCode);

	}

	public Map<String, Object> getFailedObject() {
		return failedObject;
	}

}