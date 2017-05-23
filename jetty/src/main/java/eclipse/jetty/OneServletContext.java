package eclipse.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

//无配置启动servlet  
public class OneServletContext {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		// context.setContextPath("/demo");
		context.setResourceBase("./src/main/webapp");
		context.addServlet(new ServletHolder(new HelloServlet()), "/demo/*");
		// context.addServlet(new ServletHolder(new HelloServlet("Buongiorno
		// Mondo")),"/it/*");

		server.setHandler(context);
		server.start();
		server.join();
	}
}
