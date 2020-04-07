package ch9.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch9.core.ApiRequestTemplate;
import ch9.core.JedisHelper;
import redis.clients.jedis.Jedis;

@Service("tokenExpier")
@Scope("prototype")
public class TokenExpire extends ApiRequestTemplate{
	
	private static final JedisHelper helper = JedisHelper.getInstance();
	
	public TokenExpire(Map<String, String> reqData) {
		super(reqData);
	}
	
	@Override
	public void requestParamValidation() throws RequestParamException{
		if(StringUtils.isEmpty(this.reqData.get("token"))) {
			throw new RequestParamException("token 비었음");
		}
	}
	
	@Override
	public void service() throws ServiceException{
		Jedis jedis = null;
		try {
			jedis = helper.getConnection();
			long result = jedis.xdel(this.reqData.get("token"));
			System.out.println(result);
			
			this.apiResult.addProperty("ResultCode", "200");
			this.apiResult.addProperty("message", "Success");
			this.apiResult.addProperty("token", this.reqData.get("token"));
			
		}
		catch(Exception e) {
			helper.returnResource(jedis);
		}
	}
	

}
