package eclipse.jetty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.NCSARequestLog;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class JettyDemo {

	private static final Log log = LogFactory.getLog(JettyDemo.class);

	private static final Properties config = new Properties();

	static {
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(
					JettyDemo.class.getClassLoader().getResourceAsStream("jetty.properties"));

			config.load(bufferedInputStream);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (null != bufferedInputStream)
					bufferedInputStream.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	// http://127.0.0.1:9999/demo/
	public static void main(String[] args) throws Exception {
		jetty_start();
		// start_jetty();
	}

	public static void start_jetty() throws SAXException, IOException, FileNotFoundException, Exception {

		Server server = new Server();

		// 指定自定义的jetty.xml路径
		XmlConfiguration configuration = new XmlConfiguration(
				Server.class.getClassLoader().getResourceAsStream(config.getProperty("jetty.xml")));
		configuration.configure(server);

		server.start();
	}

	// http://127.0.0.1:9999/demo/index.jsp
	public static void jetty_start() {

		try {

			String jetty_home = "demo";// 这个就是你的项目发布时候的名字

			// 用户访问记录
			HandlerCollection handlers = new HandlerCollection();
			ContextHandlerCollection contexts = new ContextHandlerCollection();
			RequestLogHandler requestLogHandler = new RequestLogHandler();
			handlers.setHandlers(new Handler[] { contexts, new DefaultHandler(), requestLogHandler });

			NCSARequestLog requestLog = new NCSARequestLog("./target/jetty-yyyy_mm_dd.request.log");
			requestLog.setRetainDays(90);
			requestLog.setAppend(true);
			requestLog.setExtended(false);
			requestLog.setLogTimeZone("GMT");
			requestLogHandler.setRequestLog(requestLog);

			Server server = new Server();

			Connector connector = new SelectChannelConnector();
			connector.setPort(Integer.parseInt(config.getProperty("jetty.port")));

			server.setConnectors(new Connector[] { connector });

			WebAppContext webapp = new WebAppContext();
			webapp.setContextPath("/" + jetty_home);// 上下文路径 比如说/demo

			// webapp.setResourceBase("./WebRoot");// 你的资源文件所在的路径 一般都在这下面
			webapp.setResourceBase(config.getProperty("resourceBase"));

			// webapp.setDefaultsDescriptor("./WebRoot/etc/webdefault.xml");
			webapp.setDefaultsDescriptor(config.getProperty("webdefault.xml"));

			server.setHandler(webapp);

			server.addHandler(handlers);

			server.start();
			server.join();

		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
