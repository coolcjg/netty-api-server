package ch9;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonObject;

import redis.clients.jedis.Jedis;

@Service("tokenIssue")
@Scope("prototype")
public class TokenIssue extends ApiRequestTemplate{
	
	private static final JedisHelper helper = JedisHelper.getInstance();
	
	@Autowired
	private SqlSession sqlSession;
	
	public TokenIssue(Map<String, String> reqData) {
		super(reqData);
	}
	
	public void requestParamValication() throws RequestParamException{
		if(StringUtils.isEmpty(this.reqData.get("userNo"))) {
			throw new RequestParamException("userNo�� �����ϴ�.");
		}
		
		if(StringUtils.isEmpty(this.reqData.get("password"))) {
			throw new RequestPramException("password�� �����ϴ�.");
		}
	}
	
	public void service() throws ServiceException{
		Jedis jedis = null;
		try {
			Map<String, Object> result = sqlSession.selectOne("users.userInfoByPassword", this.reqData);
			
			if(result !=null) {
				final long threeHour = 60*60*3;
				long issueDate = System.currentTimeMillis()/1000;
				String email = String.valueOf(result.get("USERID"));
				
				JsonObject token = new JsonObject();
				token.addProperty("issueDate", issueDate);
				token.addProperty("expireDate", issueDate + threeHour);
				token.addProperty("email", email);
				token.addProperty("userNo", reqData.get("userNo"));
				
				//token ����
				KeyMaker tokenKey = new TokenKey(email, issueDate);
				jedis = helper.getConnection();
				jedis.setex(tokenKey.getKey(), threeHour, token.toString());
				
				//helper.
				this.apiResult.addProperty("resultCode", "200");
				this.apiResult.addProperty("message", "Success");
				this.apiResult.addProperty("token", tokenKey.getKey());
			}
			else {
				//������ ����
				this.apiResult.addProperty("resultCode", "404");
			}
			helper.returnResource(jedis);
		}
		catch(Exception e) {
			helper.returnResource(jedis);
		}
	}
	
	

}
