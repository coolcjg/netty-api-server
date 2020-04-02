package ch9;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

public class ApiRequestParser extends SimpleChannelInboundHandler<FullHttpMessage>{
	private static final Logger logger = LogManager.getLogger(ApiRequestParser.class);
	private HttpRequest request;
	private JsonObject apiResult;
	
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	
	private HttpPostRequestDecoder decoder;
	
	private Map<String, String> reqData = new HashMap<String, String>();
	
	private static final Set<String> usingHeader = new HashSet<String>();
	
	static {
		usingHeader.add("token");
		usingHeader.add("email");
	}
	
	
	
	
	
	

}
