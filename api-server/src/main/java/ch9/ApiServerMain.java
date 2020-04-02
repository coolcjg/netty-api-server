package ch9;
import org.springframework.context.support.AbstractApplicationContext;

public class ApiServerMain {
	public static void main(String[] args) {
		AbstractApplicationContext springContext = null;
		
		
		try {
			
		springContext.registerShutdownHook();
		
		ApiServer server = springContext.getBean(ApiServerConfig.class);
		server.start();

		}
		finally {
			springContext.close();
		}
	}
	

}
