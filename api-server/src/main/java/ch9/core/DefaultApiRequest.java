package ch9.core;

import java.util.Map;

public class DefaultApiRequest extends ApiRequestTemplate{
	
	public DefaultApiRequest(Map<String, String> reqData) {
		super(reqData);
	}
	
	@Override
	public void service() {
		this.apiResult.addProperty("resultCode", "404");
	}

}
