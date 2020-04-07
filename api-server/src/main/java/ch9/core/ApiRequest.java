package ch9.core;

import com.google.gson.JsonObject;

import ch9.service.RequestParamException;
import ch9.service.ServiceException;

public interface ApiRequest {
	public void requestParamValidation() throws RequestParamException;
	
	public void service() throws ServiceException;
	
	public void executeService();
	
	public JsonObject getApiResult();

}
