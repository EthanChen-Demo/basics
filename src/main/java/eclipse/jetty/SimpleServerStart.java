package eclipse.jetty;

import org.eclipse.jetty.server.Server;  
import org.eclipse.jetty.webapp.WebAppContext; 

//不使用jetty.xml配置文件启动  
//http://hbiao68.iteye.com/blog/2111007  
public class SimpleServerStart {
	// http://127.0.0.1:9999/simple/
	// http://127.0.0.1:9999/simple/servlet
	public static void main(String[] args) {
		try {
			// 服务器的监听端口
			Server server = new Server(9999);
			// 关联一个已经存在的上下文
			WebAppContext context = new WebAppContext();
			// 设置描述符位置
			context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
			// 设置Web内容上下文路径
			context.setResourceBase("./src/main/webapp");
			// 设置上下文路径
			context.setContextPath("/simple");
			context.setParentLoaderPriority(true);
			server.setHandler(context);
			// 启动
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
